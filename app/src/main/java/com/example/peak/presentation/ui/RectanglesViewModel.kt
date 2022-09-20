package com.example.peak.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peak.data.repository.RectangleRepository
import com.example.peak.data.storage.RectangleEntity
import com.example.peak.data.DataState
import com.example.peak.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

@HiltViewModel
class RectanglesViewModel @Inject constructor(private val rectangleRepository: RectangleRepository) :
    ViewModel() {

    private val _uiSate = MutableLiveData<UiState>()
    val uiSate: LiveData<UiState> = _uiSate

    private val retryCount: Long = 3

    val rectangles: Flow<DataState> = flow {
        rectangleRepository.getRectangles()
            .retry(retryCount) { e ->
                // retry on any IOException but also introduce delay if retrying
                (e is IOException).also { if (it) delay(2000) }
            }
            .onStart {
                _uiSate.value = UiState.Loading
            }
            .catch { exception ->
                _uiSate.value = UiState.Error
                emit(
                    DataState.Error(
                        exception.message ?: "Exception while fetching data"
                    )
                )
            }
            .collect { rectangles ->
                _uiSate.value = UiState.Loaded
                emit(DataState.Success(rectangles))
            }
    }

    fun updateRectangle(rectangleEntity: RectangleEntity) {
        viewModelScope.launch {
            rectangleRepository.updateRectangle(rectangleEntity)
        }
    }

}