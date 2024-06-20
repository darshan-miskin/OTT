package com.example.ott.screens.common.domain.response

import com.example.ott.screens.home.domain.response.Content
import com.google.gson.annotations.SerializedName


data class ContentItems(
    @SerializedName("content") var content: ArrayList<Content> = arrayListOf()
)