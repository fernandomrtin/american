package com.example.american.main.model.datasource.remote.service

import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.main.model.entity.SessionTokenEntity
import javax.inject.Inject

class AmericanClientApi @Inject constructor(private val apiService: AmericanClientService) {
    suspend fun postDoLogin(username: String, password: String): Either<CommonError, SessionTokenEntity> = apiService.postDoLogin(username, password)
}