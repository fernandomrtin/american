package com.example.american.main.domain.models

import com.example.american.main.model.entity.SessionTokenEntity
import com.example.american.main.ui.models.SessionTokenModel

data class SessionToken(val tokenCode: String)

fun SessionTokenEntity.toDomain() = SessionToken(tokenCode)
fun SessionTokenModel.toDomain() = SessionToken(tokenCode)
