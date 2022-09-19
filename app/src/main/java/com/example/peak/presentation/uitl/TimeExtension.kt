package com.example.peak.presentation.uitl

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author yaya (@yahyalmh)
 * @since 18th September 2022
 */

object TimeExtension {
    private const val pattern = "yyyy-MM-dd"
    private val formatter = SimpleDateFormat(pattern, Locale.UK)

    fun String.toDate(): Date? = formatter.parse(this)

    fun Date.diffDays(other: Date): Long {
        val diffTime = this.time - other.time
        return TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS)
    }

}