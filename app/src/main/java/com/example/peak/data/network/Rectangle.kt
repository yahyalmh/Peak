package com.example.peak.data.network

import com.google.gson.annotations.SerializedName

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

data class Rectangle(
    val rectangleId: String,
    val x: String,
    val y: String,
    val size: String,
)

data class Rectangles(

    @SerializedName("data")
    val rectangles: List<Rectangle>
)