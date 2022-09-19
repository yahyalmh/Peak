package com.example.peak.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peak.data.RectangleRepository
import com.example.peak.data.storage.RectangleEntity
import com.example.peak.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

@HiltViewModel
class RectanglesViewModel @Inject constructor(private val rectangleRepository: RectangleRepository) :
    ViewModel() {

    val rectangles: Flow<UiState<List<RectangleEntity>>> = flow {
        rectangleRepository.getRectangles()
            .onStart { emit(UiState.Loading) }
            .catch { exception ->
                emit(
                    UiState.Error(
                        exception.message ?: "Exception while fetching data"
                    )
                )
            }
            .collect { rectangles -> emit(UiState.Success(rectangles)) }
    }

    fun updateRectangle(rectangleEntity: RectangleEntity) {
        viewModelScope.launch {
            rectangleRepository.updateRectangle(rectangleEntity)
        }
    }

}