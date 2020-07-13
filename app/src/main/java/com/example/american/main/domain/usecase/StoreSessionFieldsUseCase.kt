package com.example.american.main.domain.usecase

import com.example.american.base.domain.SuspendedUseCase
import com.example.american.main.domain.models.SessionToken
import com.example.american.main.model.AmericanClientRepository
import javax.inject.Inject

open class StoreSessionFieldsUseCase @Inject constructor(private val repository: AmericanClientRepository) : SuspendedUseCase<SessionToken, Boolean>() {
    override suspend fun run(params: SessionToken): Boolean = repository.storeSessionFields(params)

    override fun onErrorMessage(): String = "Could not retrieve comic details"
}
