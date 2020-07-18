package com.example.american.main.domain.models

import com.example.american.main.model.entity.StorageSessionObjectEntity

data class StorageSessionObject(val userId: String, val sessionToken: SessionToken, val timeStamp: Long)

fun StorageSessionObjectEntity.toDomain() = StorageSessionObject(userId, sessionToken.toDomain(), timeStamp)
