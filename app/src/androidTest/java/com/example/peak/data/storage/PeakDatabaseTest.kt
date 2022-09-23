package com.example.peak.data.storage

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.peak.data.network.Rectangle
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith

/**
 * @author yaya (@yahyalmh)
 * @since 23th September 2022
 */

@RunWith(AndroidJUnit4::class)
internal class PeakDatabaseTest {

    private lateinit var rectangleDao: RectangleDao
    private lateinit var peakDatabase: PeakDatabase

    private val rectangleEntities: List<RectangleEntity> = listOf(
        Rectangle("1", "0.5", "0.5", "0.4"),
        Rectangle("2", "0.2", "0.5", "0.4"),
    ).map { it.toEntity() }

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        peakDatabase = Room.inMemoryDatabaseBuilder(context, PeakDatabase::class.java).build()

        rectangleDao = peakDatabase.getRectangleDao()
    }

    @Test
    fun given_list_of_rectangles_WHEN_save_to_db_THEN_saved_correctly() = runBlocking {
        // when
        rectangleDao.insertAll(rectangleEntities)

        // then
        val result = rectangleDao.getAll()
        assertEquals(rectangleEntities, result)
    }

    @Test
    fun when_update_rectangle_THEN_update_successfully() = runBlocking {

        // given
        rectangleDao.insertAll(rectangleEntities)
        val rectangleEntity = rectangleEntities.first().copy(x = 0.9F, y = 0.7F)

        // when
        rectangleDao.update(rectangleEntity)

        // then
        val result = rectangleDao.getAll().first()
        assertEquals(rectangleEntity, result)
    }

    @After
    fun tearDown() {
        peakDatabase.close()
    }
}