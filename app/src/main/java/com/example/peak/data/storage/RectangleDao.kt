package com.example.peak.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

@Dao
interface RectangleDao {

    @Query("SELECT * FROM rectangles")
    suspend fun getAll(): List<RectangleEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(rectangles : List<RectangleEntity>)

    @Update
    suspend fun update(rectangleEntity: RectangleEntity)

}