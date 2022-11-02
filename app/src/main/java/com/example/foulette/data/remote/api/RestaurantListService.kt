package com.example.foulette.data.remote.api

import com.example.foulette.BuildConfig.MAPS_API_KEY
import com.example.foulette.data.remote.response.places.RestaurantListResultResponse
import retrofit2.http.GET
import com.example.foulette.util.SEARCH_NEARBY
import retrofit2.Response
import retrofit2.http.Query

interface RestaurantListService {
    @GET("${SEARCH_NEARBY}json")
    suspend fun getRestaurantList(
        @Query("location") location: String,
        @Query("key") key: String = MAPS_API_KEY,
        @Query("type") type:String = "food",
        @Query("radius") radius: Int = 1000,
        @Query("opennow") opennow: Boolean = true,
        @Query("language") language: String = "ko",
        @Query("keyword") keyword:String = "restaurant"
    ): Response<RestaurantListResultResponse>

}