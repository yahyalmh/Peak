package com.example.peak.presentation.ui

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.peak.presentation.UiState

/**
 * @author yaya (@yahyalmh)
 * @since 20th September 2022
 */

@BindingAdapter("app:loadingVisibility")
fun loadingVisibility(view: View, uiState: UiState?) {
    view.isVisible = when (uiState) {
        null -> false
        else -> uiState is UiState.Loading
    }
}

@BindingAdapter("app:errorVisibility")
fun errorVisibility(view: View, uiState: UiState?) {
    view.isVisible = when (uiState) {
        null -> false
        else -> uiState is UiState.Error
    }
}

@BindingAdapter("app:setErrorText")
fun setErrorText(view: TextView, uiState: UiState?){
    uiState?.let {
        if (uiState is UiState.Error){
            view.text = uiState.message
        }
    }
}

