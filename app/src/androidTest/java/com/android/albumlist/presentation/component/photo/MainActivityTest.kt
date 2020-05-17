package com.android.albumlist.presentation.component.photo

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.android.albumlist.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    private var mIdlingResource: IdlingResource? = null

    @Before
    fun setup() {
        mIdlingResource = mActivityTestRule.activity.countingIdlingResource
        IdlingRegistry.getInstance().register(mIdlingResource)
    }

    @Test
    fun testSearch() {
        Espresso.onView(isAssignableFrom(SearchView::class.java))
        val searchEditText = Espresso.onView(ViewMatchers.withId(R.id.sv_input))
        searchEditText.perform(click())
            .perform(typeSearchViewText("est"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
    }


    @Test
    fun testScroll() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_photos))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_title)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.iv_photo_main_Image)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun displayData() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_photos)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.pb_home)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
    }

    @Test
    fun searchIsActive() {
        Espresso.onView(ViewMatchers.withId(R.id.sv_input)).check(ViewAssertions.matches(isDisplayed()))
    }


    @Test
    fun testRefresh() {
        //Before refresh there is a list .
        Espresso.onView(ViewMatchers.withId(R.id.rv_photos)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.pb_home)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
        // do refresh .
        Espresso.onView(ViewMatchers.withId(R.id.srl_photos)).perform(swipeDown())
        //after refresh there is a list.
        Espresso.onView(ViewMatchers.withId(R.id.rv_photos)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.pb_home)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
    }


    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister()
        }
    }


    fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Change view text"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }

}
