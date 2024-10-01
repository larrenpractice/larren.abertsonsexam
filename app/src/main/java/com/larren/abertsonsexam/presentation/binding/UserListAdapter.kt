package com.larren.abertsonsexam.presentation.binding

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.larren.abertsonsexam.R


@SuppressLint("CheckResult")
@BindingAdapter("imageUrl", "placeHolder","isCircular")
fun loadImage(
    imageView: ImageView,
    imageUrl: String?,
    placeholder: Drawable?,
    isCircular: Boolean = false
) {
    if (imageUrl != null) {
        val requestOptions = RequestOptions()
            .placeholder(placeholder)
            .error(placeholder)
        if (isCircular) {
            requestOptions.circleCrop()
        }

        Glide.with(imageView.context)
            .load(imageUrl)
            .apply(requestOptions).into(imageView)
    }
}

@BindingAdapter("genderBackgroundColor")
fun setGenderBackgroundColor(view: View, gender: String?) {
    val color = when (gender) {
        "female" -> ContextCompat.getColor(
            view.context,
            R.color.female_color
        ) // Replace with your female color resource
        "male" -> ContextCompat.getColor(
            view.context,
            R.color.male_color
        ) // Replace with your male color resource
        else -> ContextCompat.getColor(
            view.context,
            R.color.default_color
        ) // Replace with your default color resource
    }
    view.setBackgroundColor(color)
}