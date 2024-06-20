package com.example.ott.screens.home.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.ott.screens.common.domain.Result
import com.example.ott.screens.common.domain.response.ContentItems
import com.example.ott.screens.home.domain.response.Content
import java.io.IOException
import javax.inject.Inject


var remoteOffset = 1
val remotePageSize = 20

@OptIn(ExperimentalPagingApi::class)
class PagingRemoteMediator @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
): RemoteMediator<Int, Content>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Content>
    ): MediatorResult {

        Log.d("asdf", "LoadType: $loadType")
        val offset = when(loadType){
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                remoteOffset+1
            }
        }

        //assume api call, which returns a result
        var result = remoteDataSource.getPagedResponse(offset)

        if(result is Result.Success<*>){
            result = result as Result.Success<ContentItems>
            remoteOffset = offset
            Log.d("asdf", "page number: ${result.data.pageNumber} \t pageContent: ${result.data.contentItems?.content?.size}")
            result.data.contentItems?.content?.let { data ->
                //store page number with data to help generate better hashcode
                data.forEach {
                    it.pageNumber = offset
                    it.key=it.hashCode()
                }
                localDataSource.withTransaction {
                    localDataSource.insert(data)
                }
                return MediatorResult.Success(data.size < remotePageSize)
            }
            return MediatorResult.Error(IOException("Invalid Data"))
        }
        else
            return MediatorResult.Error(IOException("Something went wrong"))
    }
}