package com.example.american.main.model.entity

import com.example.american.main.domain.models.StorageSessionObject

data class StorageSessionObjectEntity(val userId: String, val sessionToken: SessionTokenEntity, val timeStamp: Long)

fun StorageSessionObject.toEntity() = StorageSessionObjectEntity(userId, sessionToken.toEntity(), timeStamp)
