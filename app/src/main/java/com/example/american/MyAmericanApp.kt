package com.example.american

import android.app.Application
import com.example.american.base.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyAmericanApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        // Initialize Dagger
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
