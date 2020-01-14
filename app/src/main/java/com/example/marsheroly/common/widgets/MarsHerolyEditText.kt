package com.example.marsheroly.common.widgets

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.example.marsheroly.R
import com.example.marsheroly.common.extensions.dpToPx

class MarsHerolyEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    enum class State {
        DEFAULT, ERROR, ERROR_NO_MESSAGE
    }

    companion object {

        private val PADDING = 15.dpToPx()

    }

    private val editText = AppCompatEditText(context, attrs, defStyleAttr)
    private val errorTextView = AppCompatTextView(context)
    private var backgroundDrawableAttr: Drawable
    private var backgroundErrorDrawable: Drawable
    private var hintTextColor: Int
    private var textColor: Int
    private var errorTextColor: Int

    var text: CharSequence?
        get() = editText.text
        set(value) {
            editText.setText(value)
        }

    var errorText: CharSequence?
        get() = errorTextView.text
        set(value) {
            errorTextView.text = value
        }

    var state: State = State.DEFAULT
        set(value) {
            field = value
            when (value) {
                State.DEFAULT -> {
                    errorTextView.visibility = View.GONE
                    editText.background = backgroundDrawableAttr
                }
                State.ERROR -> {
                    errorTextView.visibility = View.VISIBLE
                    editText.background = backgroundErrorDrawable
                }

                State.ERROR_NO_MESSAGE -> {
                    errorTextView.visibility = View.GONE
                    editText.background = backgroundErrorDrawable
                }
            }
        }

    init {
        val values = context.obtainStyledAttributes(attrs, R.styleable.MarsHerolyEditText)
        val backgroundDrawableAttrRes = values.getResourceId(R.styleable.MarsHerolyEditText_backgroundDrawable, -1)
        backgroundDrawableAttr = if (backgroundDrawableAttrRes != -1) {
            ContextCompat.getDrawable(context, backgroundDrawableAttrRes)!!
        } else {
            ContextCompat.getDrawable(context, R.drawable.background_edit_text_default)!!
        }

        val backgroundErrorDrawableRes = values.getResourceId(R.styleable.MarsHerolyEditText_backgroundErrorDrawable, -1)
        backgroundErrorDrawable = if (backgroundErrorDrawableRes != -1) {
            ContextCompat.getDrawable(context, backgroundErrorDrawableRes)!!
        } else {
            ContextCompat.getDrawable(context, R.drawable.background_edit_text_error)!!
        }

        hintTextColor = values.getColor(R.styleable.MarsHerolyEditText_hintTextColor, ContextCompat.getColor(context, R.color.text2))
        textColor = values.getColor(R.styleable.MarsHerolyEditText_textColor, ContextCompat.getColor(context, R.color.black))
        errorTextColor = values.getColor(R.styleable.MarsHerolyEditText_errorTextColor, ContextCompat.getColor(context, R.color.error_red))

        values.recycle()

        editText.setPadding(PADDING)
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.setHintTextColor(hintTextColor)
        editText.setTextColor(textColor)
        errorTextView.setTextColor(errorTextColor)

        addView(editText, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        addView(errorTextView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))

        orientation = VERTICAL
        state = State.DEFAULT
    }


    fun addTextChangedListener(watcher: TextWatcher) {
        editText.addTextChangedListener(watcher)
    }

    fun setHint(hint: String) {
        editText.hint = hint
    }

}