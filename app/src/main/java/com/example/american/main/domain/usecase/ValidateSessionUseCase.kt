package com.example.american.main.domain.usecase

import arrow.core.Either
import com.example.american.base.domain.SuspendedUseCase
import com.example.american.base.network.model.CommonError
import com.example.american.main.domain.models.SessionToken
import com.example.american.main.domain.models.StorageSessionObject
import com.example.american.main.domain.models.User
import com.example.american.main.model.AmericanClientRepository
import javax.inject.Inject

open class ValidateSessionUseCase @Inject constructor(private val repository: AmericanClientRepository) : SuspendedUseCase<StorageSessionObject, Either<CommonError, Boolean>>() {
    override suspend fun run(params: StorageSessionObject): Either<CommonError, Boolean> = repository.postValidateSession(params)

    override fun onErrorMessage(): String = ""
}
