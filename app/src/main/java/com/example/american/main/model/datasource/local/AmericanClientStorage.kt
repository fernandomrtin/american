package com.example.american.main.model.datasource.local

import com.example.american.main.model.entity.StorageSessionObjectEntity

interface AmericanClientStorage {
    suspend fun storeSessionFields(storageSessionObject: StorageSessionObjectEntity): Boolean
}
