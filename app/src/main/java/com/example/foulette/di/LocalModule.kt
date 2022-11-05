package com.example.foulette.di

import android.content.Context
import androidx.room.Room
import com.example.foulette.data.local.database.HistoryDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    /*
    @Provides
    @Singleton
    fun provideConverterMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideConverter(moshi: Moshi): Converter =
        Converter(moshi)
*/
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): HistoryDatabase {
        return Room
            .databaseBuilder(context, HistoryDatabase::class.java, "database")
            //addTypeConverter(Converter)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDAO(database: HistoryDatabase) = database.historyDao()
}