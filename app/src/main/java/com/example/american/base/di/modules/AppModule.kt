package com.example.american.base.di.modules

import android.content.Context
import com.example.american.MyAmericanApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object AppModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideContext(application: MyAmericanApp): Context {
        return application.applicationContext
    }
}
