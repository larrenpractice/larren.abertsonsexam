package com.larren.abertsonsexam.domain.repository

import com.larren.abertsonsexam.data.models.RandomUserResponse
import com.larren.abertsonsexam.domain.util.Response

interface RandomUserRepository {
    suspend fun getRandomUserList(number: Int): Response<RandomUserResponse>
}