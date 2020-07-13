package com.example.american.main.model.datasource.remote.service

import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.base.network.model.CommonErrorEntity
import com.example.american.base.network.model.toDomain
import com.example.american.main.model.entity.SessionTokenEntity
import java.util.UUID

class AmericanClientServiceImpl : AmericanClientService {
    private val correctUsername = "fernando"
    private val correctPassword = "fernando"
    override suspend fun postDoLogin(
        username: String,
        password: String
    ): Either<CommonError, SessionTokenEntity> {
        return if (username == correctUsername && password == correctPassword) {
            Either.Right(SessionTokenEntity(UUID.randomUUID().toString().replace("-", "")))
        } else {
            Either.Left(CommonErrorEntity.NotFound("User Not Found").toDomain())
        }
    }
}
