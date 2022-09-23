package com.example.peak.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.peak.data.repository.RectangleRepository
import com.example.peak.presentation.UiState
import com.example.peak.rectangleEntityStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

/**
 * @author yaya (@yahyalmh)
 * @since 20th September 2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class, CoroutineTestExtensionRule::class)
internal class RectanglesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineTestExtensionRule()

    @Mock
    lateinit var repository: RectangleRepository

    lateinit var rectanglesViewModel: RectanglesViewModel

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        rectanglesViewModel = RectanglesViewModel(repository)
        whenever(repository.getRectangles()).thenReturn(flowOf(rectangleEntityStub()))
    }

    @Test
    fun `WHEN fetching data face error THEN ui state is Error`() = runTest {

        val testMessage = "Test exception"
        whenever(repository.getRectangles()).thenReturn(flow {
            throw (IOException(testMessage))
        })

        rectanglesViewModel.rectangles.collect { }

        val expectedState = rectanglesViewModel.uiSate.getOrAwaitValue()
        assertEquals(expectedState, UiState.Error(testMessage))
    }

    @Test
    fun `WHEN fetching data is successful THEN ui state is Loaded`() = runTest {

        whenever(repository.getRectangles()).thenReturn(flowOf(rectangleEntityStub()))

        rectanglesViewModel.rectangles.collect { rectangles ->
            assertEquals(rectangles, rectangleEntityStub())
        }

        val expectedState = rectanglesViewModel.uiSate.getOrAwaitValue()
        assertEquals(expectedState, UiState.Loaded)
    }

    @Test
    fun `WHEN update a rectangle THEN update happened`() = runTest(StandardTestDispatcher()) {
        val rectangleEntity = rectangleEntityStub().first()

        rectanglesViewModel.updateRectangle(rectangleEntity)

        runCurrent()

        verify(repository).updateRectangle(rectangleEntity)
    }

}