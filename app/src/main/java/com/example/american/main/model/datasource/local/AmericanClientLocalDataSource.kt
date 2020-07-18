package com.example.american.main.model.datasource.local

import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.main.model.entity.StorageSessionObjectEntity
import javax.inject.Inject

class AmericanClientLocalDataSource @Inject constructor(private val api: AmericanClientStorage) {
    suspend fun storeSessionFields(storageSessionObject: StorageSessionObjectEntity): Boolean =
        api.storeSessionFields(storageSessionObject)

    suspend fun removeStoreSessionFields(): Boolean =
        api.removeStoredSessionFields()

    suspend fun retrieveStoredSessionFields(): Either<CommonError, StorageSessionObjectEntity> =
        api.retrieveStoredSessionFields()
}
