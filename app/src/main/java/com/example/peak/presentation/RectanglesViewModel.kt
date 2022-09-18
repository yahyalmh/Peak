package com.example.peak.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.peak.data.RectangleRepository
import com.example.peak.data.storage.RectangleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

@HiltViewModel
class RectanglesViewModel @Inject constructor(private val rectangleRepository: RectangleRepository) :
    ViewModel() {

    val rectangles: LiveData<UiState<List<RectangleEntity>>> = liveData {
        rectangleRepository.getRectangles()
            .onStart { emit(UiState.Loading) }
            .catch { exception ->
                emit(
                    UiState.Error(
                        exception.message ?: "Exception while fetch data"
                    )
                )
            }
            .collect { rectangles -> emit(UiState.Success(rectangles)) }
    }

}