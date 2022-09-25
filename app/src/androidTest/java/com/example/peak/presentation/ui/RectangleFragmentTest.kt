package com.example.peak.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.peak.R
import com.example.peak.data.network.ApiStub
import com.example.peak.data.network.module.NetworkModule
import com.example.peak.data.storage.SharedKey
import com.example.peak.data.storage.module.StorageModule
import com.example.peak.presentation.ui.component.RectangleView
import com.example.peak.waitForView
import com.example.peak.withFirst
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * @author yaya (@yahyalmh)
 * @since 23th September 2022
 */
@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class RectangleFragmentTest {

    private val wiremockPort = 8080

    @Rule
    @JvmField
    public var wireMockRule = WireMockRule(wireMockConfig().port(wiremockPort))

    @get: Rule
    public var activityRule: ActivityTestRule<MainActivity> =
        object : ActivityTestRule<MainActivity>(
            MainActivity::class.java, true, false
        ) {
            override fun beforeActivityLaunched() {
                clearSharedPrefs()
                clearDatabase()
                super.beforeActivityLaunched()
            }
        }

    @Before
    fun setup() {
        val localhost = "http://127.0.0.1:$wiremockPort"
        NetworkModule.BASE_URL = localhost
    }

    @Test
    fun when_InternalServerError_THEN_errorViewIsShown() {

        ApiStub.stubRectanglesResponseWithError(501)

        launchActivity()

        onView(allOf(withId(R.id.error_view), isDisplayed()))
        onView(allOf(withId(R.id.errorTextView), isDisplayed()))
        onView(allOf(withId(R.id.retry), isDisplayed()))
    }

    @Test
    fun when_errorOccurs_THEN_retryOperationLoadData() {
        ApiStub.stubRectanglesResponseWithError(501)

        launchActivity()

        onView(allOf(withId(R.id.error_view), isDisplayed()))
        onView(allOf(withId(R.id.errorTextView), isDisplayed()))
        onView(allOf(withId(R.id.retry), isDisplayed()))

        ApiStub.stubRectanglesResponse()
        onView(allOf(withId(R.id.retry))).perform(click())
        onView(allOf(withClassName(`is`(RectangleView::class.java.canonicalName)), isDisplayed()))
    }


    @Test
    fun when_SuccessfulResponse_HTEN_ShowRectangles() {
        ApiStub.stubRectanglesResponse()

        launchActivity()

        onView(allOf(withClassName(`is`(RectangleView::class.java.canonicalName)), isDisplayed()))
    }

    @Test
    fun given_drawnRectangles_THEN_moveRectangles() {
        ApiStub.stubRectanglesResponse()

        launchActivity()

        onView(allOf(withClassName(`is`(RectangleView::class.java.canonicalName)), isDisplayed()))
        waitForView(withClassName(`is`(RectangleView::class.java.canonicalName)))

        onView(
            withFirst(
                withClassName(`is`(RectangleView::class.java.canonicalName))
            ),
        ).perform(ViewActions.swipeUp())

    }

    @After
    fun trim() {
        activityRule.finishActivity()
    }

    private fun launchActivity() =
        activityRule.launchActivity(
            Intent(
                ApplicationProvider.getApplicationContext(),
                MainActivity::class.java
            )
        )

    private fun clearSharedPrefs() =
        ApplicationProvider.getApplicationContext<Context>()
            .getSharedPreferences(SharedKey.SHARED_FILE_NAME, Context.MODE_PRIVATE).run {
                edit().clear().commit()
            }

    private fun clearDatabase() =
        ApplicationProvider.getApplicationContext<Context>()
            .deleteDatabase(StorageModule.DATABASE_NAME)
}

