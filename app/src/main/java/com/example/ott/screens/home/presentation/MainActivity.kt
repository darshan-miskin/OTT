package com.example.ott.screens.home.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.ott.R
import com.example.ott.databinding.ActivityMainBinding
import com.example.ott.screens.common.gone
import com.example.ott.screens.common.presentation.ToolbarActivity
import com.example.ott.screens.common.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ToolbarActivity() {

    private val adapter by lazy { ContentAdapter(this) }
    private val viewModel: HomeViewModel by viewModels()
    private val binding: ActivityMainBinding by bindings(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        //category aka title, would be selected by user on app first instead of fetching from api,
        // hence passing it here directly
        bindToolbar(binding.includeToolbar, "Romantic Comedy")

        binding.rvMain.adapter = adapter

        adapter.addOnPagesUpdatedListener {
            updateUi()
        }

        adapter.addLoadStateListener {
            updateRecyclerviewState(it)
        }

        lifecycleScope.launch {
            viewModel.content.collectLatest{
                adapter.submitData(it)
            }

        }
    }

    override fun onSearchText(searchQuery: String) {
        viewModel.search(searchQuery)
        //called to highlight search text in recyclerview
        adapter.setSearchQuery(searchQuery)
    }

    private fun updateRecyclerviewState(state: CombinedLoadStates) {
        when(state.append) {
            is LoadState.Error -> {
                showToast("Something went wrong")
                binding.pbItemsLoading.gone()
            }
            LoadState.Loading -> binding.pbItemsLoading.visible()
            is LoadState.NotLoading -> binding.pbItemsLoading.gone()
        }
        when(state.refresh){
            //as page size is small in json, pager immediately makes another call to fetch more data
            //so no time to show page loader
            is LoadState.Error -> {
//                binding.pageLoading.gone()
                showToast("Something went wrong")
            }
            LoadState.Loading -> {
//                binding.pageLoading.visible()
                binding.rvMain.gone()
            }
            is LoadState.NotLoading -> {
//                binding.pageLoading.gone()
                binding.rvMain.visible()
            }
        }
    }

    private fun updateUi(){
        val queryLength = viewModel.currentQuery.value.length
        val itemCount = adapter.itemCount
        if(queryLength>0 && itemCount==0){
            binding.rvMain.gone()
            binding.tvEmpty.visible()
        } else {
            binding.rvMain.visible()
            binding.tvEmpty.gone()
        }
    }

}
