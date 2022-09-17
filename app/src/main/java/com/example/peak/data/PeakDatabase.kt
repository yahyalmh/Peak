package com.example.peak.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

@Database(version = 1, entities = [RectangleEntity::class])
abstract class PeakDatabase : RoomDatabase() {

    abstract fun getRectangleDao(): RectangleDao

}