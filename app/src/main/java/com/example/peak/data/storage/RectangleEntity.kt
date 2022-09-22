package com.example.peak.data.storage

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.peak.data.network.Rectangle
import kotlin.random.Random

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
    @ColumnInfo val color: Int? = null,
)

fun Rectangle.toEntity() = RectangleEntity(
    id = rectangleId.toInt(),
    x = x.toFloat(),
    y = y.toFloat(),
    size = size.toFloat(),
)
