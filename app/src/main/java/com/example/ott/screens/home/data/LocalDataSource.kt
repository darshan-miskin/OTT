package com.example.ott.screens.home.data

import androidx.room.withTransaction
import com.example.ott.db.AppDatabase
import com.example.ott.screens.home.domain.response.Content
import javax.inject.Inject

class LocalDataSource @Inject constructor(val appDatabase: AppDatabase) {

    private val contentDao = appDatabase.contentDao()

    suspend fun withTransaction(transaction: suspend () -> Unit) = appDatabase.withTransaction { transaction.invoke() }

    suspend fun insert(list: List<Content>) = contentDao.insert(list)

    fun fetch(query: String) = contentDao.fetch(query)

    suspend fun clearAll() = contentDao.clearAll()
}