package com.example.peak

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.lang.Thread.sleep

/**
 * @author yaya (@yahyalmh)
 * @since 24th September 2022
 */

fun waitForView(
    viewMatcher: Matcher<View>,
    waitMillis: Int = 5000,
    waitMillisPerTry: Long = 100
): ViewInteraction {

    // Derive the max tries
    val maxTries = waitMillis / waitMillisPerTry.toInt()
    var tries = 0
    repeat(maxTries) {
        try {
            tries++
            // Search the root for the view
            onView(isRoot()).perform(searchFor(viewMatcher))
            // If we're here, we found our view. Now return it
            return onView(viewMatcher)
        } catch (e: Exception) {
            if (tries == maxTries) {
                throw e
            }
            sleep(waitMillisPerTry)
        }
    }

    throw Exception("Error finding a view matching $viewMatcher")

}

fun searchFor(matcher: Matcher<View>): ViewAction = object : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return isRoot()
    }

    override fun getDescription(): String {
        return "searching for view $matcher in the root view"
    }

    override fun perform(uiController: UiController, view: View) {
        var tries = 0
        val childViews: Iterable<View> = TreeIterables.breadthFirstViewTraversal(view)
        // Look for the match in the tree of child views
        childViews.forEach {
            tries++
            if (matcher.matches(it)) {
                // found the view
                return
            }
        }
        throw NoMatchingViewException.Builder()
            .withRootView(view)
            .withViewMatcher(matcher)
            .build()
    }
}


fun withFirst(matcher: Matcher<View?>): Matcher<View> {
    return object : TypeSafeMatcher<View>() {

        var isFirst = true
        override fun matchesSafely(view: View): Boolean {
            return if (isFirst && matcher.matches(view)) {
                isFirst = false
                true
            } else {
                false
            }
        }

        override fun describeTo(description: Description) {
            description.appendText("with first child view based matcher")
        }
    }
}
