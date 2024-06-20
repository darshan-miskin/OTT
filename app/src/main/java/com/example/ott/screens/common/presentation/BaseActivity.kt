package com.example.ott.screens.common.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity: AppCompatActivity() {

    protected inline fun <reified T : ViewDataBinding> bindings(@LayoutRes id: Int): Lazy<T> =
        lazy { DataBindingUtil.setContentView(this, id) }

    protected fun View.showKeyboard() {
        this.requestFocus()
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    protected fun View.hideKeyboard() {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, InputMethodManager.SHOW_IMPLICIT)
    }

    protected fun showToast(toastMsg: String, length: Int = Toast.LENGTH_LONG){
        Toast.makeText(this, toastMsg, length).show()
    }
}