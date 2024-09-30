package com.larren.abertsonsexam.domain.repository

interface RandomUserRepository {
    suspend fun getRandomUserList(number: Int): Any
}