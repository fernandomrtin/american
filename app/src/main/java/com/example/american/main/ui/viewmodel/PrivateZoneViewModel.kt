package com.example.american.main.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.american.base.navigation.NavigationCommand
import com.example.american.main.domain.usecase.RemoveStoreSessionFieldsUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class PrivateZoneViewModel @Inject constructor(private val removeStoreSessionFieldsUseCase: RemoveStoreSessionFieldsUseCase) :
    ViewModel() {

    private var _navigationCommand = MutableLiveData<NavigationCommand>()
    val navigationCommand: LiveData<NavigationCommand>
        get() = _navigationCommand

    // ///////////////////////////////////////////////////////////////////////////
    // OnClick
    // ///////////////////////////////////////////////////////////////////////////

    fun onCloseSessionButtonTapped() {
        removeFieldsInLocal()
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Methods
    // ///////////////////////////////////////////////////////////////////////////

    @VisibleForTesting
    internal fun removeFieldsInLocal(
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) =
        removeStoreSessionFieldsUseCase("", ioDispatcher) { result ->
            if (result) {
                _navigationCommand.value =
                    NavigationCommand.Back
            }
        }

    // ///////////////////////////////////////////////////////////////////////////
    // OnCleared
    // ///////////////////////////////////////////////////////////////////////////

    override fun onCleared() {
        super.onCleared()
        removeStoreSessionFieldsUseCase.cancel()
    }
}
