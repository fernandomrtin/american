package com.example.american.base.di.modules.activity

import com.example.american.base.di.modules.activity.fragment.FragmentModule
import com.example.american.base.di.modules.viewmodel.ViewModelModule
import com.example.american.base.di.scopes.ActivityScope
import com.example.american.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module(includes = [ViewModelModule::class])
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [(FragmentModule::class)])
    internal abstract fun contributeMainActivity(): MainActivity
}
