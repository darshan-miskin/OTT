package com.example.ott.screens.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.ott.R


fun View.gone(){
    this.visibility = View.GONE
}
fun View.visible(){
    this.visibility = View.VISIBLE
}
fun View.invisible(){
    this.visibility = View.INVISIBLE
}