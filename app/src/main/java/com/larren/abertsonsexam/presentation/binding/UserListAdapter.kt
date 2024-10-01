package com.larren.abertsonsexam.presentation.binding

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@SuppressLint("CheckResult")
@BindingAdapter("imageUrl", "placeHolder")
fun loadImage(
    imageView: ImageView,
    imageUrl: String?,
    placeholder: Drawable?
) {
    if (imageUrl != null) {
        val requestOptions = RequestOptions()
            .placeholder(placeholder)
            .error(placeholder)
        requestOptions.circleCrop()


        Glide.with(imageView.context)
            .load(imageUrl)
            .apply(requestOptions).into(imageView)
    }
}