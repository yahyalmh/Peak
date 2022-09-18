package com.example.peak.data.network

import com.google.gson.annotations.SerializedName

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

data class Rectangle(
    @SerializedName("rectangleId")
    val rectangleId: String,
    @SerializedName("x")
    val x: String,
    @SerializedName("y")
    val y: String,
    @SerializedName("size")
    val size: String,
)

data class Rectangles(

    @SerializedName("data")
    val rectangles: List<Rectangle>
)