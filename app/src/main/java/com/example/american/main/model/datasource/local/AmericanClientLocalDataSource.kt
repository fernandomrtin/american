package com.example.american.main.model.datasource.local

import com.example.american.main.model.entity.StorageSessionObjectEntity
import javax.inject.Inject

class AmericanClientLocalDataSource @Inject constructor(private val api: AmericanClientStorage) {
    suspend fun storeSessionFields(storageSessionObject: StorageSessionObjectEntity): Boolean =
        api.storeSessionFields(storageSessionObject)
}
