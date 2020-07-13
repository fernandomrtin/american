package com.example.american.main.domain.usecase

import com.example.american.base.domain.SuspendedUseCase
import com.example.american.main.domain.models.StorageSessionObject
import com.example.american.main.model.AmericanClientRepository
import javax.inject.Inject

open class StoreSessionFieldsUseCase @Inject constructor(private val repository: AmericanClientRepository) : SuspendedUseCase<StorageSessionObject, Boolean>() {
    override suspend fun run(params: StorageSessionObject): Boolean = repository.storeSessionFields(params)

    override fun onErrorMessage(): String = ""
}
