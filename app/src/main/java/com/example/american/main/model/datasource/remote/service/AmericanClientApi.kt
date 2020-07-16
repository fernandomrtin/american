package com.example.american.main.model.datasource.remote.service

import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.base.network.handler.runErrorHandler
import com.example.american.base.network.handler.toDomain
import com.example.american.main.model.entity.SessionTokenEntity
import javax.inject.Inject

class AmericanClientApi @Inject constructor(private val apiService2: AmericanClientService2) {
    suspend fun postDoLogin(username: String, password: String): Either<CommonError, SessionTokenEntity> = apiService2.postDoLogin(username, password).runErrorHandler().toDomain()
}
