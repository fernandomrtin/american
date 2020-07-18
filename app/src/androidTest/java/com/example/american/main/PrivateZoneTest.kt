package com.example.american.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import arrow.core.Either
import com.example.american.R
import com.example.american.main.domain.models.SessionToken
import com.example.american.main.domain.models.User
import com.example.american.main.model.AmericanClientRepository
import io.mockk.coEvery
import io.mockk.mockk
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class PrivateZoneTest : AcceptanceTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun showPrivateZoneScreen() {
        val activity = startActivity()
        fillLoginFields(givenCorrectLogin())
        performLoginClick()
        compareScreenshot(activity)
    }

    private fun givenCorrectLogin(): User {
        return User("fernando", "fernando")
    }

    private fun fillLoginFields(user: User) {
        val userNameEditText = onView(allOf(withId(R.id.et_username), isDisplayed()))
        userNameEditText.perform(
            ViewActions.replaceText(user.username),
            ViewActions.closeSoftKeyboard()
        )

        val passwordEditText = onView(allOf(withId(R.id.et_password), isDisplayed()))
        passwordEditText.perform(
            ViewActions.replaceText(user.password),
            ViewActions.closeSoftKeyboard()
        )
    }

    private fun performLoginClick() {
        val materialButton = onView(allOf(withId(R.id.login_button), withText(R.string.login), isDisplayed()))
        materialButton.perform(ViewActions.click())
        Thread.sleep(4000)
    }
}
