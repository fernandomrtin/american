package com.example.american.base.di.modules.activity.fragment

import com.example.american.base.di.scopes.FragmentScope
import com.example.american.main.ui.view.MainFragment
import com.example.american.main.ui.view.PrivateZoneFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributePrivateZoneFragment(): PrivateZoneFragment
}
