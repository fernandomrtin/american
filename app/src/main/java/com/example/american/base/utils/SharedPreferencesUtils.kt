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
    fun storeInSharedPreferences(key: String, value: Long) {
        val myEdit = sharedPreferences.edit()
        myEdit.putLong(key, value)
        myEdit.apply()
    }
    fun removeFromSharedPreferences(key: String) {
        val myEdit = sharedPreferences.edit()
        myEdit.remove(key)
        myEdit.apply()
    }
    fun getSharedPreference(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }
    fun getLongSharedPreference(key: String): Long {
        return sharedPreferences.getLong(key, 0L) ?: 0L
    }
}
