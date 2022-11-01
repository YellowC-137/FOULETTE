package com.example.foulette.di

import com.example.foulette.data.remote.datasource.RestaurantRemoteDataSource
import com.example.foulette.data.remote.datasourceimpl.RestaurantRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        RemoteDataSourceImpl: RestaurantRemoteDataSourceImpl,
    ): RestaurantRemoteDataSource
}