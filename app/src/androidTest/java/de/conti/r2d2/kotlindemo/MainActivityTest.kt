package de.conti.r2d2.kotlindemo

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testBottomSheetLaunch() {
        onView(withId(R.id.btnAddNewCity)).perform(click())
        assertThat(onView(withText("Add City")).check(matches(isDisplayed())))
        assertThat(onView(withId(R.id.cityInputView)).check(matches(isDisplayed())))
    }

    @Test
    fun testActivityIsLaunched() {
        assertThat(onView(withId(R.id.btnAddNewCity)).check(matches(isDisplayed())))
    }
}