package com.example.ott.screens.common.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ott.databinding.LayoutToolbarBinding
import com.example.ott.screens.common.gone
import com.example.ott.screens.common.visible

abstract class ToolbarActivity: BaseActivity() {

    private companion object {
        const val MIN_SEARCH_LENGTH = 3
    }

    private var isSearchVisible = false

    protected abstract fun onSearchText(searchQuery: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            isSearchVisible = it.getBoolean("isSearchVisible")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("isSearchVisible", isSearchVisible)
        super.onSaveInstanceState(outState)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            s?.let {
                if(it.length >= MIN_SEARCH_LENGTH || it.isEmpty())
                    onSearchText(it.toString())
            }
        }
    }

    /**
     * Initializes toolbar and sets title, also attaches search view text change listeners.
     * Should be called in onCreate
     * @param binding ViewDataBinding of toolbar used in activity
     * @param titleText Title to be displayed on the toolbar
     */
    protected fun bindToolbar(binding: LayoutToolbarBinding, titleText: String){
        binding.apply {
            if (isSearchVisible) {
                groupSearch.visible()
                groupToolbar.gone()
                etvSearch.addTextChangedListener(textWatcher)
            }
            title.text = titleText

            btnSearch.setOnClickListener {
                isSearchVisible = true
                groupSearch.visible()
                groupToolbar.gone()
                etvSearch.showKeyboard()
                etvSearch.addTextChangedListener(textWatcher)
            }
            btnCancel.setOnClickListener {
                isSearchVisible = false
                groupSearch.gone()
                groupToolbar.visible()
                etvSearch.hideKeyboard()
                etvSearch.removeTextChangedListener(textWatcher)
                etvSearch.text?.clear()
                onSearchText("")
            }
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}