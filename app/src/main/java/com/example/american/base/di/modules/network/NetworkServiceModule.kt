package com.example.american.base.di.modules.network

import com.example.american.main.model.datasource.remote.service.AmericanClientService2
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
object NetworkServiceModule {
    @JvmStatic
    @Provides
    fun provideRssFeedService(retrofit: Retrofit): AmericanClientService2 {
        return retrofit.create(AmericanClientService2::class.java)
    }
}