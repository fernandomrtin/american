package com.example.american.main.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.american.base.navigation.NavigationCommand
import com.example.american.main.domain.models.User
import com.example.american.main.domain.usecase.PostDoLoginUseCase
import com.example.american.main.ui.models.toModel
import com.example.american.main.ui.view.MainFragmentDirections
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MainViewModel @Inject constructor(private val postDoLoginUseCase: PostDoLoginUseCase) : ViewModel() {
    private var _navigationCommand = MutableLiveData<NavigationCommand>()
    val navigationCommand: LiveData<NavigationCommand>
        get() = _navigationCommand

    fun onLoginButtonTapped() {
        doLoginUseCase()
    }

    @VisibleForTesting
    internal fun doLoginUseCase(ioDispatcher: CoroutineDispatcher = Dispatchers.IO) =
        postDoLoginUseCase(User("test", "test"), ioDispatcher) { out ->
            out.map {
                it.toModel()
            }.fold({
            }, {
                _navigationCommand.value = NavigationCommand.To(MainFragmentDirections.actionMainScreenToPrivateZoneScreen())
            })
        }

    override fun onCleared() {
        super.onCleared()
        postDoLoginUseCase.cancel()
    }
}
