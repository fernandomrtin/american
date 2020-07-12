package com.example.american.main.model.datasource.remote.service

import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.main.model.entity.SessionTokenEntity

interface AmericanClientService {
    suspend fun postDoLogin(username: String, password: String): Either<CommonError, SessionTokenEntity>
}
