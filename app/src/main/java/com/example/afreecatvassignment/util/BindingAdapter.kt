package com.example.afreecatvassignment.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.afreecatvassignment.R

object BindingAdapter {

    @BindingAdapter("setSrcFromUrl")
    @JvmStatic
    fun setScrFromUrl(imageView: ImageView, url: String) {
        if (url.isBlank()) {
            imageView.setImageResource(R.drawable.img_empty)
        } else {
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        }
    }
}
