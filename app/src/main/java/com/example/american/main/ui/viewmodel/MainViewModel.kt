package com.example.american.main.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.american.base.navigation.NavigationCommand
import com.example.american.main.ui.view.MainFragmentDirections
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    private var _navigationCommand = MutableLiveData<NavigationCommand>()
    val navigationCommand: LiveData<NavigationCommand>
        get() = _navigationCommand

    fun onLoginButtonTapped() {
        _navigationCommand.value = NavigationCommand.To(MainFragmentDirections.actionMainScreenToPrivateZoneScreen())
    }
}
