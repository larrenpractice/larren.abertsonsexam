package com.larren.abertsonsexam.data.repository

import com.larren.abertsonsexam.data.models.RandomUserResponse
import com.larren.abertsonsexam.domain.api.service.RandomUsersApi
import com.larren.abertsonsexam.domain.repository.RandomUserRepository
import com.larren.abertsonsexam.domain.util.Response
import retrofit2.HttpException
import javax.inject.Inject

class RandomUserRepositoryImpl @Inject constructor(private val api: RandomUsersApi) :
    RandomUserRepository {

    override suspend fun getRandomUserList(number: Int): Response<RandomUserResponse> {
        return try {
            val response = api.getRandomUserList(number)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                Response.Failure(throwable = HttpException(response))
            }
        } catch (e: Exception) {
            Response.Failure(throwable = e)
        }
    }
}