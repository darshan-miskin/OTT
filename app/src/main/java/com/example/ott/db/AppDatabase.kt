package com.example.ott.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ott.screens.home.domain.response.Content

@Database(entities = [Content::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun contentDao() : ContentDao
}