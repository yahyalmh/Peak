package com.example.peak.presentation.uitl

import java.math.RoundingMode

/**
 * @author yaya (@yahyalmh)
 * @since 19th September 2022
 */

fun Float.setScale(scale: Int, mode: RoundingMode = RoundingMode.HALF_EVEN) =
    toBigDecimal().setScale(scale, mode).toFloat()
