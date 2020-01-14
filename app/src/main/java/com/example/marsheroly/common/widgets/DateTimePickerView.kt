package com.example.marsheroly.common.widgets

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.marsheroly.R
import com.example.marsheroly.common.Constants
import com.example.marsheroly.common.utils.DateUtils
import com.gery.mobile.common.extensions.format
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

class DateTimePickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    var onChangeListener: ((date: Date) -> Unit)? = null

    private val today = Calendar.getInstance()
    val date: Date
        get() {
            val minute = minuteWheelPickerView.getCurrentItemPosition() * minutesStep
            val hour = hourWheelPickerView.getCurrentItemPosition()
            val calendarPosition = getDateSelected(calendarWheelPickerView.getCurrentItemPosition())
            val day = calendarPosition.get(Calendar.DAY_OF_MONTH)
            val month = calendarPosition.get(Calendar.MONTH)
            val year = calendarPosition.get(Calendar.YEAR)
            return Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.DAY_OF_MONTH, day)
                set(Calendar.MONTH, month)
                set(Calendar.YEAR, year)
            }.time
        }

    private val calendarWheelPickerView: WheelPickerView
    private val hourWheelPickerView: WheelPickerView
    private val minuteWheelPickerView: WheelPickerView

    private val dateFormat = SimpleDateFormat("E dd MMM", Locale.getDefault())

    private val daysRange = IntArray(731).toList()
    private val todayPosition = 365
    private val hours = IntArray(24) { it }.toList()
    private val minutes = IntArray(12) { it * minutesStep }.toList()
    private val minutesStep = 5

    private var lastDate: Date? = null

    init {
        orientation = HORIZONTAL

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DateTimePickerView, defStyleAttr, 0)
        val textColor = attributes.getColor(R.styleable.DateTimePickerView_dtpvTextColor, Color.BLACK)
        attributes.recycle()

        calendarWheelPickerView = WheelPickerView(context)
        hourWheelPickerView = WheelPickerView(context)
        minuteWheelPickerView = WheelPickerView(context)

        calendarWheelPickerView.textColor(textColor)
        hourWheelPickerView.textColor(textColor)
        minuteWheelPickerView.textColor(textColor)

        hourWheelPickerView.isInfinite(true)
        minuteWheelPickerView.isInfinite(true)

        calendarWheelPickerView.setTextGravity(Gravity.CENTER_VERTICAL or Gravity.END)
        hourWheelPickerView.setTextGravity(Gravity.CENTER)
        minuteWheelPickerView.setTextGravity(Gravity.CENTER_VERTICAL or Gravity.START)

        val paramsL = LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        val paramsS = LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.6f)

        addView(calendarWheelPickerView, paramsL)
        addView(hourWheelPickerView, paramsS)
        addView(minuteWheelPickerView, paramsS)

        val hourNow = today.get(Calendar.HOUR_OF_DAY)
        val minuteNow = today.get(Calendar.MINUTE)


        calendarWheelPickerView.setData(daysRange, todayPosition) { position ->
            positionToStringDateFormatter(position)
        }
        hourWheelPickerView.setData(hours, hourNow) { position ->
            numFormatter(position)
        }
        minuteWheelPickerView.setData(minutes, minuteNow / minutesStep) { position ->
            numFormatter(position * minutesStep)
        }

        calendarWheelPickerView.addItemSelectListener {
            dispatchDateChanged()
        }

        hourWheelPickerView.addItemSelectListener {
            dispatchDateChanged()
        }

        minuteWheelPickerView.addItemSelectListener {
            dispatchDateChanged()
        }
    }

    fun setDate(date: Date) {
        val format = Constants.DATE_TIME_FORMAT
        if (date.format(format) == this.date.format(format)) {
            return
        }

        val calendar = Calendar.getInstance()
        calendar.time = date
        val dateHour = calendar.get(Calendar.HOUR_OF_DAY)
        val dateMinute = calendar.get(Calendar.MINUTE)
        val dayDifference = DateUtils.daysBetween(DateTime(today.time), DateTime(date))
        val dayPosition = todayPosition + dayDifference

        calendarWheelPickerView.setCurrentItemPosition(dayPosition)
        hourWheelPickerView.setCurrentItemPosition(dateHour)
        minuteWheelPickerView.setCurrentItemPosition(dateMinute/minutesStep)
    }

    private fun dispatchDateChanged() {
        if (!equals(DateTime(lastDate), DateTime(date))) {
            lastDate = date
            onChangeListener?.invoke(date)
        }
    }

    private fun equals(d1: DateTime?, d2: DateTime?): Boolean {
        if (d1 == null && d2 == null) return true
        if (d1 == null || d2 == null) return false
        return (d1.dayOfYear == d2.dayOfYear
                && d1.hourOfDay == d2.hourOfDay
                && d1.minuteOfHour == d2.minuteOfHour)
    }

    private fun positionToStringDateFormatter(position: Int): String {
        return if (position == todayPosition) {
            context.getString(R.string.today)
        } else {
            val dateToFormat = getDateSelected(position).time
            return dateFormat.format(dateToFormat)
        }
    }

    private fun getDateSelected(position: Int): Calendar {
        val dateToFormat = Calendar.getInstance()
        dateToFormat.add(Calendar.DAY_OF_YEAR, position - todayPosition)
        return dateToFormat
    }

    private fun numFormatter(num: Int): String {
        return if (num < 10) "0$num" else num.toString()
    }

}