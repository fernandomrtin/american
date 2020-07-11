package com.example.american.main.model.datasource.remote.service

import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.main.model.entity.SessionTokenEntity

class AmericanClientServiceImpl: AmericanClientService {
    override suspend fun postDoLogin(username: String, password: String): Either<CommonError, SessionTokenEntity> {
        return Either.Right(SessionTokenEntity("12b178be172be182bce21e122v1e701"))
    }
}