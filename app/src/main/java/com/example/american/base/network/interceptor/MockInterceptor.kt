package com.example.american.base.network.interceptor

import java.util.UUID
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {

    private val tokenCode = UUID.randomUUID().toString().replace("-", "")
    private var isValidSession = true

    private val getLoginResponse = """{"tokenCode": "$tokenCode"}"""
    private val getCheckSessionTokenResponse = """{"isValid": "$isValidSession"}"""

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        var code = 200
        val errorStatusCode = 404
        var responseString = ""
        when {
            uri.endsWith("error") -> {
                code = errorStatusCode
                responseString = getCheckSessionTokenResponse
            }
            uri.endsWith("login") -> {
                responseString = getLoginResponse
            }
            uri.endsWith("validatesession") -> {
                responseString = getCheckSessionTokenResponse
            }
        }

        return chain.proceed(chain.request())
            .newBuilder()
            .code(code)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                responseString.toByteArray().toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}
