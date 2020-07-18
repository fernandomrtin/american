package com.example.american.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.american.R
import com.example.american.main.domain.models.User
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginTest : AcceptanceTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun showMainActivity() {
        val activity = startActivity()
        compareScreenshot(activity)
    }

    @Test
    fun showMainActivityWithFilledFields() {
        val activity = startActivity()
        fillLoginFields(givenCorrectLogin())
        compareScreenshot(activity)
    }

    @Test
    fun showWrongLoginWarning() {
        val activity = startActivity()
        fillLoginFields(givenInCorrectLogin())
        performLoginClick()
        compareScreenshot(activity)
    }

    private fun givenCorrectLogin(): User {
        return User("fernando", "fernando")
    }

    private fun givenInCorrectLogin(): User {
        return User("error", "error")
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
        val materialButton =
            onView(allOf(withId(R.id.login_button), withText("Login"), isDisplayed()))
        materialButton.perform(ViewActions.click())
        Thread.sleep(2000)
    }
}
