package com.example.american.base.di.modules.network

import com.example.american.main.model.datasource.remote.service.AmericanClientService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
object NetworkServiceModule {
    @JvmStatic
    @Provides
    fun provideRssFeedService(retrofit: Retrofit): AmericanClientService {
        return retrofit.create(AmericanClientService::class.java)
    }
}