package com.example.american.base.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import javax.inject.Inject


class SharedPreferencesUtils @Inject constructor(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)

    fun storeInSharedPreferences(key: String, value: String) {
        val myEdit = sharedPreferences.edit()
        myEdit.putString(key, value)
        myEdit.apply()
    }
}