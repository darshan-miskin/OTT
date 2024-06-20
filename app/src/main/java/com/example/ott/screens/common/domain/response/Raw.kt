package com.example.ott.screens.common.domain.response

import com.google.gson.annotations.SerializedName

data class Raw(
    @SerializedName("page") var apiResponse: ApiResponse<ContentItems>
)