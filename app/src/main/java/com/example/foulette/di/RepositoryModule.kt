package com.example.foulette.di

import com.example.foulette.data.local.repositoryImpl.HistoryRepositoryImpl
import com.example.foulette.data.remote.repositoryImpl.RemoteRepositoryImpl
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
    abstract fun bindRemoteRepository(
        RepositoryImpl: RemoteRepositoryImpl,
    ): RestaurantRepository

    @Binds
    @Singleton
    abstract fun bindLocalRepository(
        historyRepositoryImpl: HistoryRepositoryImpl
    ): HistoryRepositoryImpl


}