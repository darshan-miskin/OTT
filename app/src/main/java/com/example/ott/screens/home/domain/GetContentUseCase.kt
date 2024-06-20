package com.example.ott.screens.home.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.ott.screens.home.data.LocalDataSource
import com.example.ott.screens.home.data.PagingRemoteMediator
import com.example.ott.screens.home.domain.response.Content
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Using a use case instead of repository.
 * For this scenario, a usecase gets the job done without needing a repository
 */
class GetContentUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val pagingRemoteMediator: PagingRemoteMediator
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getContentPaging(query: String): Flow<PagingData<Content>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = true
            ),
            remoteMediator = pagingRemoteMediator,
            pagingSourceFactory = { localDataSource.fetch(query)}
        ).flow
    }
}