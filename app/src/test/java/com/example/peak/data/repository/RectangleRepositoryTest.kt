package com.example.peak.data.repository

import android.content.SharedPreferences
import com.example.peak.data.network.PeakApi
import com.example.peak.data.storage.RectangleDao
import com.example.peak.data.storage.RectangleEntity
import com.example.peak.data.storage.SharedKey
import com.example.peak.rectangleEntityStub
import com.example.peak.rectanglesStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

/**
 * @author yaya (@yahyalmh)
 * @since 20th September 2022
 */
@RunWith(MockitoJUnitRunner::class)
internal class RectangleRepositoryTest {
    @Mock
    lateinit var peakApi: PeakApi

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var rectangleDao: RectangleDao

    private lateinit var rectangleRepository: RectangleRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        rectangleRepository = RectangleRepository(peakApi, sharedPreferences, rectangleDao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN first time fetching data THEN data fetched from api`() = runTest {
        whenever(sharedPreferences.getLong(SharedKey.LAST_UPDATE_DATE_KEY, Long.MIN_VALUE))
            .thenReturn(Long.MIN_VALUE)
        whenever(peakApi.getRectangles()).thenReturn(rectanglesStub())

        rectangleRepository.getRectangles().first()

        verify(peakApi).getRectangles()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN is not first time but a week passed THEN data fetched from api`() = runTest {
        val tenDaysLater = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -10)
        }.time

        whenever(sharedPreferences.getLong(SharedKey.LAST_UPDATE_DATE_KEY, Long.MIN_VALUE))
            .thenReturn(tenDaysLater.time)

        whenever(peakApi.getRectangles()).thenReturn(rectanglesStub())

        rectangleRepository.getRectangles().first()

        verify(peakApi).getRectangles()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN is not first time and a week not passed THEN data not fetched from api`() = runTest {
        val fourDaysLater = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -4)
        }.time

        whenever(sharedPreferences.getLong(SharedKey.LAST_UPDATE_DATE_KEY, Long.MIN_VALUE))
            .thenReturn(fourDaysLater.time)

        whenever(peakApi.getRectangles()).thenReturn(rectanglesStub())

        rectangleRepository.getRectangles().first()

        verify(peakApi, never()).getRectangles()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN data fetched from api THEN data saved to db`() = runTest {
        whenever(sharedPreferences.getLong(SharedKey.LAST_UPDATE_DATE_KEY, Long.MIN_VALUE))
            .thenReturn(Long.MIN_VALUE)
        whenever(peakApi.getRectangles()).thenReturn(rectanglesStub())

        rectangleRepository.getRectangles().first()

        verify(rectangleDao).insertAll(anyList())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN data fetched from api THEN today date save to shared preferences`() = runTest {
        whenever(sharedPreferences.getLong(SharedKey.LAST_UPDATE_DATE_KEY, Long.MIN_VALUE))
            .thenReturn(Long.MIN_VALUE)
        whenever(peakApi.getRectangles()).thenReturn(rectanglesStub())

        rectangleRepository.getRectangles().first()

        verify(sharedPreferences, times(1)).edit()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN data requested THEN data read from database`() = runTest {
        whenever(sharedPreferences.getLong(SharedKey.LAST_UPDATE_DATE_KEY, Long.MIN_VALUE))
            .thenReturn(Long.MIN_VALUE)
        whenever(peakApi.getRectangles()).thenReturn(rectanglesStub())

        rectangleRepository.getRectangles().first()

        verify(rectangleDao, times(1)).getAll()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN update a rectangle THEN rectangle updated in db`() = runTest {
        whenever(sharedPreferences.getLong(SharedKey.LAST_UPDATE_DATE_KEY, Long.MIN_VALUE))
            .thenReturn(Long.MIN_VALUE)
        whenever(peakApi.getRectangles()).thenReturn(rectanglesStub())

        val rectangleEntity = rectangleEntityStub().first()
        rectangleRepository.updateRectangle(rectangleEntity)

        verify(rectangleDao).update(rectangleEntity)
    }
}