package com.example.american.main.domain.usecase

import arrow.core.Either
import com.example.american.base.domain.SuspendedUseCase
import com.example.american.base.network.model.CommonError
import com.example.american.main.domain.models.StorageSessionObject
import com.example.american.main.model.AmericanClientRepository
import javax.inject.Inject

open class RetrieveStoreSessionFieldsUseCase @Inject constructor(private val repository: AmericanClientRepository) : SuspendedUseCase<String, Either<CommonError, StorageSessionObject>>() {
    override suspend fun run(params: String): Either<CommonError, StorageSessionObject> = repository.retrieveStoredSessionFields()

    override fun onErrorMessage(): String = "Could not retrieve comic details"
}
