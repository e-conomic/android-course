package com.e_conomic.weatherapp

import com.e_conomic.weatherapp.ui.activities.MainActivity
import android.support.test.rule.ActivityTestRule
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`

import org.junit.Rule
import org.junit.Test

class SimpleInstrumentationTest {

    @get:Rule val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test fun itemClick_navigatesToDetail() {
        onView(withId(R.id.forecastList)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.weatherDescription))
                .check(matches(isAssignableFrom(TextView::class.java)))
    }

    @Test fun modifyCityId_changesToolbarTitle() {
        openActionBarOverflowOrOptionsMenu(activityRule.activity)
        onView(withText(R.string.settings)).perform(click())
        onView(withId(R.id.settingsCityId)).perform(replaceText("2610613"))
        pressBack()
        onView(isAssignableFrom(Toolbar::class.java)).check(matches(
                withToolbarTitle(`is`("Vejle (DK)"))
        ))
    }

    private fun withToolbarTitle(textMatcher: Matcher<CharSequence>): Matcher<Any> =
            object : BoundedMatcher<Any, Toolbar>(Toolbar::class.java) {

                override fun matchesSafely(toolbar: Toolbar): Boolean {
                    return textMatcher.matches(toolbar.title)
                }

                override fun describeTo(description: Description) {
                    description.appendText("with toolbar title: ")
                    textMatcher.describeTo(description)
                }

            }
}