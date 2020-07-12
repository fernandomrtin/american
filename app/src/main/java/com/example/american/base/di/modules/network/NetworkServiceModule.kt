package com.example.american.base.di.modules.network

import com.example.american.main.model.datasource.remote.service.AmericanClientService
import com.example.american.main.model.datasource.remote.service.AmericanClientServiceImpl
import dagger.Module
import dagger.Provides

@Module
object NetworkServiceModule {
    @JvmStatic
    @Provides
    fun provideAmericanClientService(): AmericanClientService {
        return AmericanClientServiceImpl()
    }
}
