package com.example.american.base.di.modules

import android.content.Context
import com.example.american.MyAmericanApp
import com.example.american.base.di.modules.network.NetworkServiceModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkServiceModule::class])
internal object AppModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideContext(application: MyAmericanApp): Context {
        return application.applicationContext
    }
}
