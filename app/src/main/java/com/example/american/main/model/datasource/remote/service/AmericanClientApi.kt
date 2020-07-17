package com.example.american.main.model.datasource.remote.service

import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.base.network.handler.runErrorHandler
import com.example.american.base.network.handler.toDomain
import com.example.american.base.utils.SharedPreferencesUtils
import com.example.american.main.model.entity.SessionTokenEntity
import com.example.american.main.model.entity.ValidateSessionEntity
import javax.inject.Inject

class AmericanClientApi @Inject constructor(private val apiService: AmericanClientService) {
    suspend fun postDoLogin(username: String, password: String): Either<CommonError, SessionTokenEntity> = apiService.postDoLogin(username, password, if (username == "error") "error" else "").runErrorHandler().toDomain()
    suspend fun postValidateSession(userId: String, tokenCode: String, timeStamp: Long): Either<CommonError, ValidateSessionEntity> = apiService.postValidateSession(userId, tokenCode, timeStamp).runErrorHandler().toDomain()
}
