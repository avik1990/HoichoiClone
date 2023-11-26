package com.app.hoichoiclone.utility.bindings

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.app.hoichoiclone.utility.customviews.timeago.RelativeTimeTextView

@BindingMethods(
    BindingMethod(
        type = RelativeTimeTextView::class,
        attribute = "rttv:relative_time_prefix",
        method = "setPrefix"
    ),
    BindingMethod(
        type = RelativeTimeTextView::class,
        attribute = "rttv:relative_time_suffix",
        method = "setSuffix"
    )
)
object RelativeTimeTextViewBindingAdapter {
    @BindingAdapter("rttv:reference_time")
    @JvmStatic
    fun setReferenceTime(view: RelativeTimeTextView, time: Long) {
        view.setReferenceTime(time)
    }
}
