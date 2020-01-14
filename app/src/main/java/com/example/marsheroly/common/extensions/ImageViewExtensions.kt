package com.example.marsheroly.common.extensions

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.marsheroly.presentation.GlideApp
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * Method to load a circular image.
 * @param url of the image to be loaded
 * @param placeholder for when there is a problem loading the image
 */
fun ImageView.loadRoundedPicture(url: String?, placeholder: Int? = null) {
    val request = GlideApp.with(context!!)
        .load(url)
        .apply(RequestOptions.circleCropTransform())

    placeholder?.let { request.placeholder(it) }

    request.into(this)
}

fun ImageView.loadRoundedPicture(uri: Uri, placeholder: Int? = null) {
    val request = GlideApp.with(context!!)
        .load(uri)
        .apply(RequestOptions.circleCropTransform())

    placeholder?.let { request.placeholder(it) }

    request.into(this)
}

/**
 * Method to load an image with rounded corners.
 * @param url of the image to be loaded
 * @param radius in dp
 * @param cornerType corners to be rounded - default: all corners -
 * @param placeholder for when there is a problem loading the image
 */
fun ImageView.loadRoundedPicture(
    url: String?,
    placeholder: Int? = null,
    radius: Int = 0,
    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL
) {
    val radiusPx = radius.dpToPx()
    val multi = MultiTransformation<Bitmap>(
        CenterCrop(), RoundedCornersTransformation(radiusPx, 0, cornerType)
    )
    val request = GlideApp.with(context!!)
        .load(url)
        .apply(RequestOptions.bitmapTransform(multi))

    placeholder?.let { request.placeholder(it) }

    request.into(this)
}

fun ImageView.loadRoundedPicture(
    drawableSrc: Int, radius: Int,
    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL
) {
    val radiusPx = radius.dpToPx()
    val multi = MultiTransformation<Bitmap>(
        CenterCrop(), RoundedCornersTransformation(radiusPx, 0, cornerType)
    )
    val request = GlideApp.with(context!!)
        .load(ContextCompat.getDrawable(this.context, drawableSrc))
        .apply(RequestOptions.bitmapTransform(multi))

    request.into(this)
}