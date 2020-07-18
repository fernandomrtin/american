package com.example.american.main.model.entity

import com.example.american.main.domain.models.SessionToken

data class SessionTokenEntity(val tokenCode: String)

fun SessionToken.toEntity() = SessionTokenEntity(tokenCode)
