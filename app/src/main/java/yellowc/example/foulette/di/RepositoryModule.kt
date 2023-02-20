package yellowc.example.foulette.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yellowc.example.foulette.data.local.repositoryImpl.HistoryRepositoryImpl
import yellowc.example.foulette.data.remote.repositoryImpl.RemoteRepositoryImpl
import yellowc.example.foulette.domain.repositories.HistoryRepository
import yellowc.example.foulette.domain.repositories.RestaurantRepository
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
    ): HistoryRepository


}