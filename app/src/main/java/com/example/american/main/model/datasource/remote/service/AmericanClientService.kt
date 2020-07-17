package com.example.american.main.model.datasource.remote.service

import com.example.american.main.model.entity.SessionTokenEntity
import com.example.american.main.model.entity.ValidateSessionEntity
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface AmericanClientService {
    @FormUrlEncoded
    @POST("/login{errorExtension}")
    suspend fun postDoLogin(@Field("username") username: String, @Field("password") password: String, @Path("errorExtension") errorExtension: String): Response<SessionTokenEntity>
    @FormUrlEncoded
    @POST("/validatesession")
    suspend fun postValidateSession(@Field("userId") userId: String, @Field("tokencode") tokenCode: String, @Field("timestamp") timeStamp: Long): Response<ValidateSessionEntity>
}
