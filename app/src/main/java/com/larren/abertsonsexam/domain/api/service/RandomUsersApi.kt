package com.larren.abertsonsexam.domain.api.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUsersApi {
    @GET("api/")
    suspend fun getRandomUserList(@Query("results") results: Int): Response<String>
}
