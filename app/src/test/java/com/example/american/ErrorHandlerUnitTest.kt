package com.example.american

import com.example.american.base.network.handler.runErrorHandler
import com.example.american.base.network.handler.toDomain
import com.example.american.base.network.model.CommonError
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ErrorHandlerUnitTest {

    @Test
    fun `success if 404 is NotFound error`() {
        val response: Response<Any> = Response.error(
            404,
            "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )

        response.runErrorHandler().toDomain().fold({
            assertEquals(CommonError.NotFound, it)
        }, {})
    }

    @Test
    fun `success if 499 is Server error`() {
        val response: Response<Any> = Response.error(
            499,
            "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )

        response.runErrorHandler().toDomain().fold({
            assertEquals(CommonError.ServerError, it)
        }, {})
    }

    @Test
    fun `success if 500 is Server error`() {
        val response: Response<Any> = Response.error(
            500,
            "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )

        response.runErrorHandler().toDomain().fold({
            assertEquals(CommonError.ServerError, it)
        }, {})
    }

    @Test
    fun `success if 403 is Forbidden error`() {
        val response: Response<Any> = Response.error(
            403,
            "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )

        response.runErrorHandler().toDomain().fold({
            assertEquals(CommonError.Forbidden, it)
        }, {})
    }

    @Test
    fun `success if 401 is Client error`() {
        val response: Response<Any> = Response.error(
            401,
            "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )

        response.runErrorHandler().toDomain().fold({
            assertEquals(CommonError.ClientError, it)
        }, {})
    }

    @Test
    fun `success if 400 is Client error`() {
        val response: Response<Any> = Response.error(
            400,
            "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )

        response.runErrorHandler().toDomain().fold({
            assertEquals(CommonError.ClientError, it)
        }, {})
    }
}
