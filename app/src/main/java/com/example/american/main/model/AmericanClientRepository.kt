package com.example.american.main.model

import arrow.core.Either
import com.example.american.base.network.model.CommonError
import com.example.american.main.domain.models.SessionToken
import com.example.american.main.domain.models.User
import com.example.american.main.domain.models.toDomain
import com.example.american.main.model.datasource.local.AmericanClientLocalDataSource
import com.example.american.main.model.datasource.remote.AmericanClientDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AmericanClientRepository @Inject constructor(private val americanClientDataSource: AmericanClientDataSource, private val americanClientLocalDataSource: AmericanClientLocalDataSource) {
    suspend fun postDoLogin(user: User): Either<CommonError, SessionToken> = americanClientDataSource.postDoLogin(user.username, user.password).map {
        it.toDomain()
    }
    suspend fun storeSessionFields(sessionToken: SessionToken): Boolean = true
}
