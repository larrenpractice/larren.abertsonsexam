package com.larren.abertsonsexam.data.repository

import com.google.gson.Gson
import com.larren.abertsonsexam.data.models.ErrorResponse
import com.larren.abertsonsexam.data.models.RandomUserResponse
import com.larren.abertsonsexam.domain.api.service.RandomUsersApi
import com.larren.abertsonsexam.domain.repository.RandomUserRepository
import com.larren.abertsonsexam.domain.util.Response
import javax.inject.Inject

class RandomUserRepositoryImpl @Inject constructor(private val api: RandomUsersApi) :
    RandomUserRepository {

    override suspend fun getRandomUserList(number: Int): Response<RandomUserResponse> {
        return try {
            val response = api.getRandomUserList(number)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Failure(errorMessage = errorMessage)
            }
        } catch (e: Exception) {
            Response.Failure(errorMessage = e.message ?: "${e.cause?.message}")
        }
    }

    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            val gson = Gson()
            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
            errorResponse.error
        } catch (e: Exception) {
            "Unknown error"
        }
    }
}