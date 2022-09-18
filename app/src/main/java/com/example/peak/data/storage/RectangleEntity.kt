package com.example.peak.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.peak.data.network.Rectangle

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

@Entity(tableName = "rectangles")
data class RectangleEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo val x: Float,
    @ColumnInfo val y: Float,
    @ColumnInfo val size: Float,
)

fun Rectangle.toEntity() = RectangleEntity(
    id = rectangleId.toInt(),
    x = x.toFloat(),
    y = y.toFloat(),
    size = size.toFloat()
)
