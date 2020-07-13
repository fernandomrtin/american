package com.example.american.main.model.datasource.local

import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.base.network.model.CommonErrorEntity
import com.example.american.base.network.model.toDomain
import com.example.american.base.utils.SharedPreferencesUtils
import com.example.american.main.model.entity.SessionTokenEntity
import com.example.american.main.model.entity.StorageSessionObjectEntity
import org.joda.time.Days
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AmericanClientStorageImpl @Inject constructor(private val sharedPreferencesUtils: SharedPreferencesUtils) :
    AmericanClientStorage {
    private val userIdKey = "USERID_KEY"
    private val sessionTokenKey = "SESSIONTOKEN_KEY"
    private val timeStampKey = "TIMESTAMP_KEY"
    override suspend fun storeSessionFields(storageSessionObject: StorageSessionObjectEntity): Boolean {
        sharedPreferencesUtils.storeInSharedPreferences(userIdKey, storageSessionObject.userId)
        sharedPreferencesUtils.storeInSharedPreferences(
            sessionTokenKey,
            storageSessionObject.sessionToken.tokenCode
        )
        sharedPreferencesUtils.storeInSharedPreferences(timeStampKey, System.currentTimeMillis())
        return true
    }

    override suspend fun retrieveStoredSessionFields(): Either<CommonError, StorageSessionObjectEntity> {
        val userId = sharedPreferencesUtils.getSharedPreference(userIdKey)
        val sessionToken =
            SessionTokenEntity(sharedPreferencesUtils.getSharedPreference(sessionTokenKey))
        val timeStampMillis = sharedPreferencesUtils.getLongSharedPreference(timeStampKey)
        if (timeStampMillis != 0L && userId.isNotEmpty() && sessionToken.tokenCode.isNotEmpty()) {
            val storedDate = Date(timeStampMillis)
            val diff = Date(System.currentTimeMillis()).time - storedDate.time
            val result = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
            return if (result > 1) {
                sharedPreferencesUtils.removeFromSharedPreferences(userIdKey)
                sharedPreferencesUtils.removeFromSharedPreferences(sessionTokenKey)
                sharedPreferencesUtils.removeFromSharedPreferences(timeStampKey)
                Either.Left(CommonErrorEntity.NotFound("Session Token Not Found").toDomain())
            } else {
                Either.Right(StorageSessionObjectEntity(userId, sessionToken))
            }
        } else {
            sharedPreferencesUtils.removeFromSharedPreferences(userIdKey)
            sharedPreferencesUtils.removeFromSharedPreferences(sessionTokenKey)
            sharedPreferencesUtils.removeFromSharedPreferences(timeStampKey)
            return Either.Left(CommonErrorEntity.NotFound("Session Token Not Found").toDomain())
        }
    }
}
