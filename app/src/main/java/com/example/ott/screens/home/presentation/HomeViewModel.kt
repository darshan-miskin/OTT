package com.example.ott.screens.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ott.screens.home.domain.GetContentUseCase
import com.example.ott.screens.home.domain.response.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val contentUseCase: GetContentUseCase) : ViewModel(){
    private val _currentQuery = MutableStateFlow("")
    val currentQuery =_currentQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val content: Flow<PagingData<Content>> = _currentQuery.flatMapLatest { query ->
        contentUseCase.getContentPaging(query)
    }.cachedIn(viewModelScope)

    fun search(query: String) {
        _currentQuery.value = query
    }
}