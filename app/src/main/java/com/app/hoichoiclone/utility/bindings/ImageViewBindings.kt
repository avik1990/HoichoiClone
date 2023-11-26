package com.app.hoichoiclone.utility.bindings

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.size.Scale
import java.lang.Exception

@BindingAdapter("imageUrl", "placeholderImageSrc")
fun imageLoadingFromString(
    imageView: ImageView,
    imageUrl: String?,
    placeholderImageSrc: Drawable
) {
    try {
        imageView.load(imageUrl) {
            scale(Scale.FILL)
        }
    } catch (err: Exception) {
        imageView.setImageDrawable(placeholderImageSrc)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("signedFloatVal")
fun timeStampTodateFormat(textView: TextView, timeStamp: String?) {
}
