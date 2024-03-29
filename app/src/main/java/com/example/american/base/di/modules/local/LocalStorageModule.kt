package com.example.american.base.di.modules.local

import com.example.american.base.utils.SharedPreferencesUtils
import com.example.american.main.model.datasource.local.AmericanClientStorage
import com.example.american.main.model.datasource.local.AmericanClientStorageImpl
import dagger.Module
import dagger.Provides

@Module
object LocalStorageModule {
    @JvmStatic
    @Provides
    fun provideAmericanClientStorage(sharedPreferencesUtils: SharedPreferencesUtils): AmericanClientStorage {
        return AmericanClientStorageImpl(sharedPreferencesUtils)
    }
}
