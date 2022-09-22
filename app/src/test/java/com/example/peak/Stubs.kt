package com.example.peak

import com.example.peak.data.network.Rectangle
import com.example.peak.data.network.Rectangles
import com.example.peak.data.storage.toEntity

/**
 * @author yaya (@yahyalmh)
 * @since 20th September 2022
 */

fun rectanglesStub() = Rectangles(
    listOf(
        Rectangle("1", "0.5", "0.5", "0.4"),
        Rectangle("1", "0.2", "0.5", "0.4"),
        Rectangle("1", "0.5", "0.8", "0.4"),
        Rectangle("1", "0.4", "0.5", "0.1"),
        Rectangle("1", "0.5", "0.1", "0.4"),
    )
)

fun rectangleEntityStub() = rectanglesStub().rectangles.map { it.toEntity() }