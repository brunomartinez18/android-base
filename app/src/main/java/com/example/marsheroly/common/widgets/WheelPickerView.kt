package com.example.marsheroly.common.widgets

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.marsheroly.R
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow


class WheelPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    companion object {

        private const val VISIBLE_ITEM_COUNT = 7
        private val ITEM_HEIGHT_PX = dpToPx(25f)
        private val ITEM_TEXT_SIZE_PX = spToPx(18f)
        private const val DEFAULT_IS_INFINITE = false
        private const val SHRINK_AMOUNT = 0.30f

    }


    private val childViewMetrics = ChildViewMetrics(VERTICAL)
    private val snapHelper = LinearSnapHelper()
    private var isInfinite: Boolean
    private var textColor: Int = Color.BLACK
    private var textGravity: Int = Gravity.CENTER
    private val extraOffsetPosition: Int get() = if (isInfinite) 0 else VISIBLE_ITEM_COUNT / 2
    private val listeners = mutableListOf<(position: Int) -> Unit>()
    private var position: Int? = null
    private var itemCount = 0
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            handleScroll(true)
        }
    }

    init {
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.WheelPickerView, defStyleAttr, 0)
        isInfinite = attributes.getBoolean(R.styleable.WheelPickerView_wpvInfinite, DEFAULT_IS_INFINITE)
        textColor = attributes.getColor(R.styleable.WheelPickerView_wpvTextColor, textColor)

        attributes.recycle()

        overScrollMode = View.OVER_SCROLL_NEVER
        setHasFixedSize(true)
        layoutManager = LayoutManager(context)
        snapHelper.attachToRecyclerView(this)
        onFlingListener = snapHelper

        val mScrollTouchListener = object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        }
        addOnItemTouchListener(mScrollTouchListener)
    }

    fun isInfinite(isInfinite: Boolean) {
        this.isInfinite = isInfinite
    }

    fun textColor(color: Int) {
        this.textColor = color
    }

    fun setTextGravity(gravity: Int) {
        textGravity = gravity
    }

    private fun handleScroll(vibrate: Boolean) {
        val itemAtCenter = findChildViewUnder(width / 2f, height / 2f)
        itemAtCenter?.let {
            val position = getChildAdapterPosition(it)
            if (position != this.position && position != NO_POSITION) {
                this.position = position

                if (vibrate) {
                    vibrate(context)
                }

                dispatchPositionChanged()
            }
        }
    }

    private fun dispatchPositionChanged() {
        listeners.forEach { listener ->
            val position = getCurrentItemPosition()
            listener.invoke(position)
        }
    }

    private fun getRealPosition(position: Int) = position - extraOffsetPosition

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val height = ITEM_HEIGHT_PX * VISIBLE_ITEM_COUNT
        super.onMeasure(widthSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
    }

    fun setData(
        items: List<Any>,
        initialSelection: Int = 0,
        formatter: ((position: Int) -> String)? = null
    ) {
        itemCount = items.count()
        val data = mutableListOf<Any?>()
        (1..extraOffsetPosition).map { data.add(null) }
        data.addAll(items)
        (1..extraOffsetPosition).map { data.add(null) }

        adapter =
            WheelAdapter(
                data,
                formatter,
                { position -> smoothScrollToPosition(position) },
                textColor
            )
        position = initialSelection + extraOffsetPosition

        post {
            removeOnScrollListener(scrollListener)

            if (isInfinite) {
                scrollToPositionInternal(getScrollPosition(initialSelection))
                dispatchPositionChanged()
            } else {
                val view = layoutManager?.findViewByPosition(position!!)
                val scrollDistance = if (view == null) {
                    initialSelection * ITEM_HEIGHT_PX
                } else {
                    val childCenterLocation = childViewMetrics.center(view).toInt()
                    childCenterLocation - getCenterLocation()
                }
                scrollBy(0, scrollDistance)
                dispatchPositionChanged()
            }

            postDelayed({
                addOnScrollListener(scrollListener)
            }, 200)
        }
    }

    private fun scrollToPositionInternal(position: Int) {
        (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
    }

    fun getCurrentItemPosition(): Int {
        val pos = position ?: return 0
        if (itemCount == 0) return 0
        val real = getRealPosition(pos)
        return if (isInfinite) (real % itemCount) else real
    }

    fun setCurrentItemPosition(position: Int) {
        this.position = position + extraOffsetPosition
        post {
            val scrollPosition = getScrollPosition(position)
            scrollToPositionInternal(scrollPosition)
        }
    }

    fun addItemSelectListener(listener: ((position: Int) -> Unit)?) {
        if (listener == null || listeners.contains(listener)) return
        listeners.add(listener)
    }

    private fun getScrollPosition(position: Int): Int {
        return if (isInfinite) {
            val multi = (Int.MAX_VALUE / 2) / itemCount
            (multi * itemCount) + position - (VISIBLE_ITEM_COUNT / 2)
        } else {
            position
        }
    }

    private fun getPercentageFromCenter(child: View): Float {
        val center = getCenterLocation().toFloat()
        val childCenter = childViewMetrics.center(child)

        val offSet = max(center, childCenter) - min(center, childCenter)
        val maxOffset = center + childViewMetrics.size(child)

        return offSet / maxOffset
    }

    private fun getCenterLocation() = measuredHeight / 2

    @SuppressLint("MissingPermission")
    private fun vibrate(context: Context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            return
        }

        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator ?: return

        val milliseconds = 10L
        val amplitude = 30

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(milliseconds, amplitude)
            vibrator.vibrate(effect)
        } else {
            vibrator.vibrate(milliseconds)
        }
    }

    private inner class LayoutManager(context: Context) :
        CenterLayoutManager(context, RecyclerView.VERTICAL, false) {

        override fun isAutoMeasureEnabled(): Boolean {
            return false
        }

        override fun generateDefaultLayoutParams(): LayoutParams {
            return LayoutParams(LayoutParams.MATCH_PARENT, ITEM_HEIGHT_PX)
        }

        override fun generateLayoutParams(lp: ViewGroup.LayoutParams?): LayoutParams {
            return generateDefaultLayoutParams()
        }

        override fun generateLayoutParams(c: Context?, attrs: AttributeSet?): LayoutParams {
            return generateDefaultLayoutParams()
        }

        override fun scrollVerticallyBy(dy: Int, recycler: Recycler?, state: State?): Int {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            for (i in 0 until childCount) {
                val child = getChildAt(i) ?: continue

                val percentage = getPercentageFromCenter(child)
                val factor = max(1f - percentage, 1f - SHRINK_AMOUNT)

                if ((textGravity and Gravity.START) == Gravity.START) {
                    child.pivotX = 0f
                } else if ((textGravity and Gravity.END) == Gravity.END) {
                    child.pivotX = child.width.toFloat()
                }

                child.scaleX = factor
                child.scaleY = factor
                child.alpha = factor.toDouble().pow(3.0).toFloat()
            }
            return scrolled
        }

        override fun onLayoutChildren(recycler: Recycler?, state: State?) {
            super.onLayoutChildren(recycler, state)
            scrollVerticallyBy(0, recycler, state)
        }

    }

    private inner class WheelAdapter(
        private val items: List<Any?>,
        private val formatter: ((position: Int) -> String)?,
        private val clickListener: (position: Int) -> Unit,
        private val textColor: Int
    ) : RecyclerView.Adapter<WheelAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val context = parent.context
            val textView = TextView(context)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ITEM_TEXT_SIZE_PX.toFloat())
            textView.setTextColor(textColor)
            textView.gravity = textGravity
            return ViewHolder(textView)
        }

        override fun getItemCount(): Int = if (isInfinite) Int.MAX_VALUE else items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val pos = if (isInfinite) position % items.count() else position
            holder.bind(pos)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            internal fun bind(position: Int) {
                val item = items[position]
                val textView = itemView as TextView
                if (item == null) {
                    textView.text = null
                    textView.setOnClickListener(null)
                } else {
                    textView.text = formatter?.invoke(getRealPosition(position)) ?: item.toString()
                    textView.setOnClickListener {
                        clickListener.invoke(adapterPosition)
                    }
                }
            }

        }

    }

}

private class ChildViewMetrics(private val orientation: Int) {

    fun size(view: View): Int {
        return if (orientation == RecyclerView.VERTICAL) view.height else view.width

    }

    fun location(view: View): Float {
        return if (orientation == RecyclerView.VERTICAL) view.y else view.x

    }

    fun center(view: View): Float {
        return location(view) + size(view) / 2
    }

}

open class CenterLayoutManager(
    context: Context,
    orientation: Int,
    reverseLayout: Boolean
) : LinearLayoutManager(context, orientation, reverseLayout) {

    companion object {

        private const val ANIMATION_DURATION = 200

    }

    private var smoothScroller: CenterSmoothScroller

    init {
        smoothScroller = CenterSmoothScroller(context)
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State?,
        position: Int
    ) {
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private class CenterSmoothScroller internal constructor(context: Context) :
        LinearSmoothScroller(context) {

        override fun calculateDtToFit(
            viewStart: Int,
            viewEnd: Int,
            boxStart: Int,
            boxEnd: Int,
            snapPreference: Int
        ): Int {
            return boxStart + (boxEnd - boxStart) / 2 - (viewStart + (viewEnd - viewStart) / 2)
        }

        override fun calculateTimeForScrolling(dx: Int): Int {
            return ANIMATION_DURATION
        }

        override fun calculateTimeForDeceleration(dx: Int): Int {
            return ANIMATION_DURATION
        }

    }

}

private fun dpToPx(value: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        Resources.getSystem().displayMetrics
    ).toInt()
}

private fun spToPx(value: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        value,
        Resources.getSystem().displayMetrics
    ).toInt()
}