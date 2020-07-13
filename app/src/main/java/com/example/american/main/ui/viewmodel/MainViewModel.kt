package com.example.american.main.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.american.base.navigation.NavigationCommand
import com.example.american.main.domain.models.StorageSessionObject
import com.example.american.main.domain.models.User
import com.example.american.main.domain.models.toDomain
import com.example.american.main.domain.usecase.PostDoLoginUseCase
import com.example.american.main.domain.usecase.RetrieveStoreSessionFieldsUseCase
import com.example.american.main.domain.usecase.StoreSessionFieldsUseCase
import com.example.american.main.ui.models.SessionTokenModel
import com.example.american.main.ui.models.toModel
import com.example.american.main.ui.view.MainFragmentDirections
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MainViewModel @Inject constructor(
    private val postDoLoginUseCase: PostDoLoginUseCase,
    private val storeSessionFieldsUseCase: StoreSessionFieldsUseCase,
    private val retrieveStoreSessionFieldsUseCase: RetrieveStoreSessionFieldsUseCase
) : ViewModel() {
    private var _navigationCommand = MutableLiveData<NavigationCommand>()
    val navigationCommand: LiveData<NavigationCommand>
        get() = _navigationCommand

    private var _sessionToken = MutableLiveData<SessionTokenModel>()
    val sessionToken: LiveData<SessionTokenModel>
        get() = _sessionToken

    private var _errorVisibility = MutableLiveData<Boolean>()
    val errorVisibility: LiveData<Boolean>
        get() = _errorVisibility

    fun init() {
        _navigationCommand.value = null
        retrieveFieldsFromLocal()
    }

    // ///////////////////////////////////////////////////////////////////////////
    // OnClick
    // ///////////////////////////////////////////////////////////////////////////

    fun onLoginButtonTapped(username: String, password: String) {
        doLoginUseCase(user = User(username, password))
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Methods
    // ///////////////////////////////////////////////////////////////////////////

    @VisibleForTesting
    internal fun doLoginUseCase(ioDispatcher: CoroutineDispatcher = Dispatchers.IO, user: User) =
        postDoLoginUseCase(user, ioDispatcher) { out ->
            out.map {
                it.toModel()
            }.fold({
                _errorVisibility.value = true
            }, {
                _sessionToken.value = it
                _errorVisibility.value = false
                storeFieldsInLocal(user = user, sessionTokenModel = it)
            })
        }

    @VisibleForTesting
    internal fun storeFieldsInLocal(
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
        user: User,
        sessionTokenModel: SessionTokenModel
    ) =
        storeSessionFieldsUseCase(
            StorageSessionObject(
                user.username,
                sessionTokenModel.toDomain()
            )
        ) { result ->
            if (result) {
                _navigationCommand.value =
                    NavigationCommand.To(MainFragmentDirections.actionMainScreenToPrivateZoneScreen())
            }
        }

    @VisibleForTesting
    internal fun retrieveFieldsFromLocal(
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) =
        retrieveStoreSessionFieldsUseCase("") { out ->
            out.map {
            }.fold({
            }, {
                _navigationCommand.value =
                    NavigationCommand.To(MainFragmentDirections.actionMainScreenToPrivateZoneScreen())
            })
        }

    // ///////////////////////////////////////////////////////////////////////////
    // OnCleared
    // ///////////////////////////////////////////////////////////////////////////

    override fun onCleared() {
        super.onCleared()
        postDoLoginUseCase.cancel()
        storeSessionFieldsUseCase.cancel()
    }
}
