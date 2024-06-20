package com.example.ott.screens.home.data

import android.content.res.Resources
import androidx.annotation.RawRes
import com.example.ott.R
import com.example.ott.screens.common.domain.Result
import com.example.ott.screens.common.domain.response.Raw
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * passing resources here only because it is needed to fetch json file from raw folder while
 * also maintaining architecture. In release projects, resources is not needed.
 */
class RemoteDataSource @Inject constructor(val resources: Resources) {

    private fun readRawJson(@RawRes rawResId: Int): Raw {
        return Gson().fromJson(
            resources.openRawResource(rawResId).bufferedReader().use { it.readText() },
            Raw::class.java
        )
    }

    /**
     * Simulate api call by a delay
     */
    suspend fun getPagedResponse(page: Int) = withContext(Dispatchers.IO) {
        delay(600)
        val raw = when (page) {
            1 -> readRawJson(R.raw.listing_page_1)
            2 -> readRawJson(R.raw.listing_page_2)
            3 -> readRawJson(R.raw.listing_page_3)
            else -> return@withContext Result.Failure
        }
        return@withContext Result.Success(raw.apiResponse)
    }
}