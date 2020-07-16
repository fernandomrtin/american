package com.example.american.base.network.interceptor

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        var code = 200
        val errorStatusCode = 404
        var responseString = ""
        if (chain.request().url.username == "error") {
            code = errorStatusCode
        }
        when {
            uri.endsWith("login") -> {
                responseString = getLoginResponse
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

const val getLoginResponse = """
{
	"tokenCode": "MDEwOlJlcG9zaXRvcnkxMjk2MjY5"
}
"""