package com.example.american.main.domain.usecase

import com.example.american.base.domain.SuspendedUseCase
import com.example.american.main.model.AmericanClientRepository
import javax.inject.Inject

open class RemoveStoreSessionFieldsUseCase @Inject constructor(private val repository: AmericanClientRepository) : SuspendedUseCase<String, Boolean>() {
    override suspend fun run(params: String): Boolean = repository.removeStoreSessionFields()

    override fun onErrorMessage(): String = ""
}
