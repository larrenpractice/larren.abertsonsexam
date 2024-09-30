package com.larren.abertsonsexam.domain.usecases

import com.larren.abertsonsexam.domain.repository.RandomUserRepository
import javax.inject.Inject

class GetRandomUserList @Inject constructor(private val repository: RandomUserRepository) {
    suspend operator fun invoke(number: Int) = repository.getRandomUserList(number)
}