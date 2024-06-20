package com.example.ott.screens.common.domain

import com.example.ott.screens.common.domain.response.ApiResponse

sealed class Result{
    data class Success<T>(val data: ApiResponse<T>) : Result()
    data object Failure : Result()
    data object Loading : Result()
}