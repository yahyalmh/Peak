package com.example.peak.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

@Entity(tableName = "rectangles")
data class RectangleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val x: Float,
    @ColumnInfo val y: Float,
    @ColumnInfo val size: Float,
)