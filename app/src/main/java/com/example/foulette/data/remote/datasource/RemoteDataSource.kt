package com.example.foulette.data.remote.datasource

import com.example.foulette.data.remote.response.kakaoplaces.KakaoSearchResultResponse
import com.example.foulette.data.remote.response.places.RestaurantListResultResponse
import com.example.foulette.data.remote.response.tmap.TmapRouteResultResponse
import com.example.foulette.domain.models.Result

interface RemoteDataSource {
    suspend fun getRestaurantList(myLoc: String): Result<RestaurantListResultResponse>

    suspend fun getKakaoCategoryList(
        latitude: String,
        longtitude: String
    ): Result<KakaoSearchResultResponse>

    suspend fun getTmapRoute(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        endName: String
    ): com.example.foulette.domain.models.Result<TmapRouteResultResponse>
}