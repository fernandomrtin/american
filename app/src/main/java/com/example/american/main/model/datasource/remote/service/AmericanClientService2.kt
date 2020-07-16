package com.example.american.main.model.datasource.remote.service

import com.example.american.main.model.entity.SessionTokenEntity
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AmericanClientService2 {
    @FormUrlEncoded
    @POST("/login")
    suspend fun postDoLogin(@Field("username") username: String, @Field("password") password: String): Response<SessionTokenEntity>
}
