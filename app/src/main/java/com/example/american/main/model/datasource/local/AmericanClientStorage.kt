package com.example.american.main.model.datasource.local

import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.main.model.entity.StorageSessionObjectEntity

interface AmericanClientStorage {
    suspend fun storeSessionFields(storageSessionObject: StorageSessionObjectEntity): Boolean
    suspend fun retrieveStoredSessionFields(): Either<CommonError, StorageSessionObjectEntity>
    suspend fun removeStoredSessionFields(): Boolean
}
