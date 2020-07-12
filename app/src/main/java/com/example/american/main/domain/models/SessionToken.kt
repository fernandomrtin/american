package com.example.american.main.domain.models

import com.example.american.main.model.entity.SessionTokenEntity

data class SessionToken(val tokenCode: String)

fun SessionTokenEntity.toDomain() = SessionToken(tokenCode)
