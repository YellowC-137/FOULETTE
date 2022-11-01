package com.example.foulette.di

import com.example.foulette.data.repositoryImpl.RestaurantRepositoryImpl
import com.example.foulette.domain.repositories.RestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        RepositoryImpl: RestaurantRepositoryImpl,
    ): RestaurantRepository

}