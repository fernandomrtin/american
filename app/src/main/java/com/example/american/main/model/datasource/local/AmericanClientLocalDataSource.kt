package com.example.american.main.model.datasource.local

import com.example.american.main.domain.models.SessionToken
import javax.inject.Inject

class AmericanClientLocalDataSource @Inject constructor() {
    suspend fun storeSessionFields(sessionToken: SessionToken): Boolean = true
}
