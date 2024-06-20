package com.example.ott.screens.common.domain.response

import com.google.gson.annotations.SerializedName


data class ApiResponse<T>(
    @SerializedName("title") var title: String? = null,
    @SerializedName("total-content-items") var totalContentTtems: String? = null,
    @SerializedName("page-num") var pageNumber: String? = null,
    @SerializedName("page-size") var pageSize: String? = null,
    @SerializedName("content-items") var contentItems: T? = null
)