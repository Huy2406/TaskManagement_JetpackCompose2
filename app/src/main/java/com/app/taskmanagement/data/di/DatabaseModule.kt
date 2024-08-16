package com.app.taskmanagement.data.di

import android.content.Context
import androidx.room.Room
import com.app.taskmanagement.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app-database"
    ).build()

    @Provides
    @Singleton
    fun provideProjectDao(appDatabase: AppDatabase) = appDatabase.projectDao()

    @Provides
    @Singleton
    fun provideTaskDao(appDatabase: AppDatabase) = appDatabase.taskDao()
}
