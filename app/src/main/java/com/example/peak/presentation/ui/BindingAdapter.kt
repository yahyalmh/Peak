package com.example.peak.presentation.ui

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.peak.presentation.UiState

/**
 * @author yaya (@yahyalmh)
 * @since 20th September 2022
 */

@BindingAdapter("app:isLoading")
fun isLoading(view: View, uiState: UiState?) {
    view.isVisible = when (uiState) {
        null -> false
        else -> uiState is UiState.Loading
    }
}