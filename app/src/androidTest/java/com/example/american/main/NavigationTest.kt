package com.example.american.main

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class NavigationTest : AcceptanceTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun showMainActivity() {
        val activity = startActivity()
        compareScreenshot(activity)
    }
}
