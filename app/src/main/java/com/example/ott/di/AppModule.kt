package com.example.ott.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import com.example.ott.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun appDatabase(application: Application) =
        Room.databaseBuilder(application, AppDatabase::class.java, "ott_db").build()

    @Singleton
    @Provides
    fun contentDao(appDatabase: AppDatabase) = appDatabase.contentDao()


    /**
     * check [com.example.ott.screens.home.data.RemoteDataSource] to understand why this is being used here.
     *
     * This isn't causing any memory leaks in profiler.
     */
    @Singleton
    @Provides
    fun resources(@ApplicationContext context: Context): Resources = context.resources
}