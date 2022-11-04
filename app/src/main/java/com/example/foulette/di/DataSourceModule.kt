package com.example.foulette.di

import com.example.foulette.data.remote.datasource.RemoteDataSource
import com.example.foulette.data.remote.datasourceimpl.RemoteDataSourceImpl
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
        RemoteDataSourceImpl: RemoteDataSourceImpl,
    ): RemoteDataSource

}