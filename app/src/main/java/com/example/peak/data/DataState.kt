package com.example.peak.data

/**
 * @author yaya (@yahyalmh)
 * @since 18th September 2022
 */

sealed class DataState {
    data class Success<T>(val data: T) : DataState()
    data class Error(val message: String) : DataState()
}




