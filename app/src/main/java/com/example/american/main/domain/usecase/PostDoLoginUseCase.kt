package com.example.american.main.domain.usecase

import arrow.core.Either
import com.example.american.base.domain.SuspendedUseCase
import com.example.american.base.network.model.CommonError
import com.example.american.main.domain.models.SessionToken
import com.example.american.main.domain.models.User
import com.example.american.main.model.AmericanClientRepository
import javax.inject.Inject

open class PostDoLoginUseCase @Inject constructor(private val repository: AmericanClientRepository) : SuspendedUseCase<User, Either<CommonError, SessionToken>>() {
    override suspend fun run(params: User): Either<CommonError, SessionToken> = repository.postDoLogin(params)

    override fun onErrorMessage(): String = ""
}
