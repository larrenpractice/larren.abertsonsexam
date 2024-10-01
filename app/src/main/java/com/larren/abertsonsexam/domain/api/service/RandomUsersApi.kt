package com.larren.abertsonsexam.domain.api.service

import com.larren.abertsonsexam.data.models.RandomUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUsersApi {
    @GET("api/")
    suspend fun getRandomUserList(
        @Query("results") results: Int,
        @Query("exc") inc: String = "login,registered,nat,info,id"
    ): Response<RandomUserResponse>
}
