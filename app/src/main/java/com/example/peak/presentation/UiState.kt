package com.example.peak.presentation

/**
 * @author yaya (@yahyalmh)
 * @since 18th September 2022
 */

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
