package com.example.american.main.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.american.base.navigation.NavigationCommand
import com.example.american.main.domain.models.StorageSessionObject
import com.example.american.main.domain.models.User
import com.example.american.main.domain.models.toDomain
import com.example.american.main.domain.usecase.*
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
    private val retrieveStoreSessionFieldsUseCase: RetrieveStoreSessionFieldsUseCase,
    private val validateSessionUseCase: ValidateSessionUseCase,
    private val removeStoreSessionFieldsUseCase: RemoveStoreSessionFieldsUseCase
) : ViewModel() {
    private var _navigationCommand = MutableLiveData<NavigationCommand>()
    val navigationCommand: LiveData<NavigationCommand>
        get() = _navigationCommand

    private var _errorVisibility = MutableLiveData<Boolean>()
    val errorVisibility: LiveData<Boolean>
        get() = _errorVisibility

    private var _progressVisibility = MutableLiveData<Boolean>()
    val progressVisibility: LiveData<Boolean>
        get() = _progressVisibility

    private var _sessionToken = MutableLiveData<SessionTokenModel>()
    val sessionToken: LiveData<SessionTokenModel>
        get() = _sessionToken

    fun init() {
        _progressVisibility.value = true
        _navigationCommand.value = null
        retrieveFieldsFromLocal()
    }

    // ///////////////////////////////////////////////////////////////////////////
    // OnClick
    // ///////////////////////////////////////////////////////////////////////////

    fun onLoginButtonTapped(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            _progressVisibility.value = true
            doLoginUseCase(user = User(username, password))
        }
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Methods
    // ///////////////////////////////////////////////////////////////////////////

    @VisibleForTesting
    internal fun doLoginUseCase(ioDispatcher: CoroutineDispatcher = Dispatchers.IO, user: User, timeStamp: Long = System.currentTimeMillis()) =
        postDoLoginUseCase(user, ioDispatcher) { out ->
            out.map {
                it.toModel()
            }.fold({
                _progressVisibility.value = false
                _errorVisibility.value = true
            }, {
                _sessionToken.value = it
                _progressVisibility.value = false
                _errorVisibility.value = false
                storeFieldsInLocal(user = user, sessionTokenModel = it, timeStamp = timeStamp)
            })
        }

    @VisibleForTesting
    internal fun storeFieldsInLocal(
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
        user: User,
        sessionTokenModel: SessionTokenModel, timeStamp: Long
    ) =
        storeSessionFieldsUseCase(
            StorageSessionObject(
                user.username,
                sessionTokenModel.toDomain(), timeStamp
            ), ioDispatcher
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
        retrieveStoreSessionFieldsUseCase("", ioDispatcher) { out ->
            out.map {
                it
            }.fold({
                _progressVisibility.value = false
            }, {
                validateSession(storageSessionObject = it)
            })
        }

    @VisibleForTesting
    internal fun validateSession(
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO, storageSessionObject: StorageSessionObject
    ) =
        validateSessionUseCase(storageSessionObject, ioDispatcher) { out ->
            out.map {
                it
            }.fold({
                removeFieldsInLocal()
                _progressVisibility.value = false
                _errorVisibility.value = true
            }, {
                if (it) {
                    _progressVisibility.value = false
                    _navigationCommand.value =
                        NavigationCommand.To(MainFragmentDirections.actionMainScreenToPrivateZoneScreen())
                }
            })
        }

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
        postDoLoginUseCase.cancel()
        storeSessionFieldsUseCase.cancel()
        retrieveStoreSessionFieldsUseCase.cancel()
        validateSessionUseCase.cancel()
        removeStoreSessionFieldsUseCase.cancel()
    }
}
