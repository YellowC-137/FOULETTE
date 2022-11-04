package com.example.foulette.di

import com.example.foulette.BuildConfig
import com.example.foulette.data.remote.api.KakaoCategorySearchService
import com.example.foulette.data.remote.api.RestaurantListService
import com.example.foulette.data.remote.api.TmapRouteService
import com.example.foulette.util.KAKAO_SEARCH_CATEGORY
import com.example.foulette.util.SEARCH_NEARBY
import com.example.foulette.util.TMAP_ROUTE
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    //Retrofit
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class GoogleMapsRetrofit

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class KakaoRetrofit

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class TmapRetrofit


    @Provides
    @Singleton
    @GoogleMapsRetrofit
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SEARCH_NEARBY)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    @KakaoRetrofit
    fun provideKakaoRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(KAKAO_SEARCH_CATEGORY)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    @TmapRetrofit
    fun provideTmapRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TMAP_ROUTE)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    //Api
    @Provides
    @Singleton
    fun provideApiService(
        @GoogleMapsRetrofit retrofit: Retrofit,
    ): RestaurantListService {
        return retrofit.create(RestaurantListService::class.java)
    }

    @Provides
    @Singleton
    fun provideKakaoApiService(
        @KakaoRetrofit retrofit: Retrofit,
    ): KakaoCategorySearchService {
        return retrofit.create(KakaoCategorySearchService::class.java)
    }

    @Provides
    @Singleton
    fun provideTmapApiService(
        @TmapRetrofit retrofit: Retrofit,
    ): TmapRouteService {
        return retrofit.create(TmapRouteService::class.java)
    }

    //HttpClient

    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    //Converter

    @Provides
    @Singleton
    fun providesConverterFactory() =
        json.asConverterFactory("application/json".toMediaType())

    //Interceptor

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }
}