package com.example.american.base.di

import com.example.american.MyAmericanApp
import com.example.american.base.di.modules.AppModule
import com.example.american.base.di.modules.activity.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyAmericanApp): Builder

        fun build(): AppComponent
    }

    fun inject(myAmericanApp: MyAmericanApp)
}