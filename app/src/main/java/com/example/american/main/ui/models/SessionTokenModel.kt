package com.example.american.main.ui.models

import com.example.american.main.domain.models.SessionToken

data class SessionTokenModel(val tokenCode: String)

fun SessionToken.toModel() = SessionTokenModel(tokenCode)
