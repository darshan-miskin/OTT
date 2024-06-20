package com.example.ott.screens.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.ott.R

@BindingAdapter("setDrawable")
fun getDrawable(imageView: ImageView, string: String?)  {
    imageView.setImageResource(when(string){
        "poster1.jpg" -> R.drawable.poster1
        "poster2.jpg" -> R.drawable.poster2
        "poster3.jpg" -> R.drawable.poster3
        "poster4.jpg" -> R.drawable.poster4
        "poster5.jpg" -> R.drawable.poster5
        "poster6.jpg" -> R.drawable.poster6
        "poster7.jpg" -> R.drawable.poster7
        "poster8.jpg" -> R.drawable.poster8
        "poster9.jpg" -> R.drawable.poster9
        else -> R.drawable.placeholder_for_missing_posters
    })
}