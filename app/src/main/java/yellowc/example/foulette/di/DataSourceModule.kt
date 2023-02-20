package yellowc.example.foulette.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yellowc.example.foulette.data.local.datasource.LocalDataSource
import yellowc.example.foulette.data.local.datasourceImpl.LocalDatasourceImpl
import yellowc.example.foulette.data.remote.datasource.RemoteDataSource
import yellowc.example.foulette.data.remote.datasourceimpl.RemoteDataSourceImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        RemoteDataSourceImpl: RemoteDataSourceImpl,
    ): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(
        localDatasourceImpl: LocalDatasourceImpl,
    ): LocalDataSource

}