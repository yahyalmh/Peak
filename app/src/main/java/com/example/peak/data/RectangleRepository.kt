package com.example.peak.data

import android.content.SharedPreferences
import com.example.peak.data.network.PeakApi
import com.example.peak.data.storage.RectangleDao
import com.example.peak.data.storage.RectangleEntity
import com.example.peak.data.storage.SharedKey
import com.example.peak.data.storage.toEntity
import com.example.peak.presentation.uitl.TimeExtension.diffDays
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

@ViewModelScoped
class RectangleRepository @Inject constructor(
    private val peakApi: PeakApi,
    private val sharedPreferences: SharedPreferences,
    private val rectangleDao: RectangleDao,
) {

    fun getRectangles() = flow {
        if (isFirstTime() || isWeekPassed()) {
            refreshData()
        }
        emit(rectangleDao.getAll())
    }.flowOn(Dispatchers.IO)

    private suspend fun refreshData() {
        peakApi
            .getRectangles()
            .rectangles
            .map { it.toEntity() }
            .run { rectangleDao.insertAll(this) }
            .also { saveCurrentDate() }
    }

    private fun isWeekPassed(): Boolean =
        when (val lastUpdatingTime =
            sharedPreferences.getLong(SharedKey.LAST_UPDATE_DATE_KEY, Long.MIN_VALUE)) {
            Long.MIN_VALUE -> false
            else -> {
                val today = Calendar.getInstance().time
                today.diffDays(Date(lastUpdatingTime)) > Calendar.DAY_OF_WEEK
            }
        }

    private fun isFirstTime(): Boolean =
        with(sharedPreferences.getLong(SharedKey.LAST_UPDATE_DATE_KEY, Long.MIN_VALUE)) {
            this == Long.MIN_VALUE
        }

    private fun saveCurrentDate() =
        with(sharedPreferences) {
            val today = Calendar.getInstance().time
            edit()
                .putLong(SharedKey.LAST_UPDATE_DATE_KEY, today.time)
                .apply()
        }

    suspend fun updateRectangle(rectangleEntity: RectangleEntity) {
        rectangleDao.update(rectangleEntity)
    }
}
