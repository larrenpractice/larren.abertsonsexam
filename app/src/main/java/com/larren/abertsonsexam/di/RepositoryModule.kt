package com.larren.abertsonsexam.di

import com.larren.abertsonsexam.data.repository.RandomUserRepositoryImpl
import com.larren.abertsonsexam.domain.repository.RandomUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRandomUserRepository(repositoryImpl: RandomUserRepositoryImpl): RandomUserRepository
}