package com.example.marsheroly.common.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.marsheroly.R
import kotlinx.android.synthetic.main.custom_profile_picture.view.*


class CustomProfilePicture @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var pictureSrc: Int = -1
        set(value) {
            field = value
            custom_profile_picture.setImageResource(value)
        }

    var borderTint: Int = -1
        set(value) {
            field = value
            custom_profile_picture_border.setColorFilter(value)
        }

    var showBorder: Boolean = true
        set(value) {
            field = value
            if (value) {
                custom_profile_picture_border.visibility = VISIBLE
            } else {
                custom_profile_picture_border.visibility = INVISIBLE
            }
            invalidate()
            requestLayout()
        }

    init {
        View.inflate(context, R.layout.custom_profile_picture, this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomProfilePicture,
            0, 0
        ).apply {
            try {
                pictureSrc = getResourceId(R.styleable.CustomProfilePicture_pictureSrc, R.drawable.ic_no_picture_placeholder)
                showBorder = getBoolean(R.styleable.CustomProfilePicture_showBorder, true)
                borderTint = getColor(R.styleable.CustomProfilePicture_borderTint, ContextCompat.getColor(context, R.color.white))

            } finally {
                recycle()
            }
        }
    }

    fun getPictureImageView(): ImageView {
        return custom_profile_picture
    }
}