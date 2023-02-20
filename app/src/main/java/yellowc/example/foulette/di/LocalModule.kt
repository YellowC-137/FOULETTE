package yellowc.example.foulette.di

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yellowc.example.foulette.FouletteApplication
import yellowc.example.foulette.data.local.database.HistoryDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(): HistoryDatabase {
        return Room
            .databaseBuilder(
                FouletteApplication.ApplicationContext(),
                HistoryDatabase::class.java,
                "history_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDAO(database: HistoryDatabase) = database.historyDao()
}