package com.example.american.main.model.datasource.local

import com.example.american.base.utils.SharedPreferencesUtils
import com.example.american.main.model.entity.StorageSessionObjectEntity
import javax.inject.Inject

class AmericanClientStorageImpl @Inject constructor(private val sharedPreferencesUtils: SharedPreferencesUtils) :
    AmericanClientStorage {
    private val userIdKey = "USERID_KEY"
    private val sessionTokenKey = "SESSIONTOKEN_KEY"
    override suspend fun storeSessionFields(storageSessionObject: StorageSessionObjectEntity): Boolean {
        sharedPreferencesUtils.storeInSharedPreferences(userIdKey, storageSessionObject.userId)
        sharedPreferencesUtils.storeInSharedPreferences(
            sessionTokenKey,
            storageSessionObject.sessionToken.tokenCode
        )
        return true
    }
}
