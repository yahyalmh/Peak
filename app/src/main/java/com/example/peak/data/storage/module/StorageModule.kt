package com.example.peak.data.storage.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.peak.data.storage.PeakDatabase
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
    fun provideDatabase(@ApplicationContext applicationContext: Context) =
        Room.databaseBuilder(
            applicationContext,
            PeakDatabase::class.java,
            "peak-db"
        ).build()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext applicationContext: Context): SharedPreferences =
        applicationContext.getSharedPreferences("Peak-pref", Context.MODE_PRIVATE)

}