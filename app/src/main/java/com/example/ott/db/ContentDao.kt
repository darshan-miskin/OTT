package com.example.ott.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ott.screens.home.domain.response.Content

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<Content>) : List<Long>

    @Query("select * from content where name like '%' || :query || '%'")
    fun fetch(query: String): PagingSource<Int, Content>

    @Query("delete from content")
    suspend fun clearAll()
}