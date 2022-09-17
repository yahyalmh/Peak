package com.example.peak.data

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
    fun getAll(): List<RectangleEntity>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg rectangles: RectangleEntity)

    @Update
    fun update(rectangleEntity: RectangleEntity)

}