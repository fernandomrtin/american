package com.example.american.main.model.datasource.remote

import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.main.model.datasource.remote.service.AmericanClientApi
import com.example.american.main.model.entity.SessionTokenEntity
import com.example.american.main.model.entity.ValidateSessionEntity
import javax.inject.Inject

class AmericanClientDataSource @Inject constructor(private val api: AmericanClientApi) {
    suspend fun postDoLogin(username: String, password: String): Either<CommonError, SessionTokenEntity> = api.postDoLogin(username, password)
    suspend fun postValidateSession(userId: String, tokenCode: String, timeStamp: Long): Either<CommonError, ValidateSessionEntity> =
        api.postValidateSession(userId, tokenCode, timeStamp)
}
