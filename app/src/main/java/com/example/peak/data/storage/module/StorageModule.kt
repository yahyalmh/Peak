package com.example.peak.data.storage.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.peak.data.storage.PeakDatabase
import com.example.peak.data.storage.SharedKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    fun provideRectangleDao(appDatabase: PeakDatabase) = appDatabase.getRectangleDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): PeakDatabase {
        return Room.databaseBuilder(
            applicationContext,
            PeakDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext applicationContext: Context): SharedPreferences =
        applicationContext.getSharedPreferences(SharedKey.SHARED_FILE_NAME, Context.MODE_PRIVATE)

    companion object {
        const val DATABASE_NAME = "peak-db"
    }
}