package com.example.foulette.domain.repositories

import com.example.foulette.domain.models.KakaoResult
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.models.TmapRouteResult

interface RestaurantRepository {
    suspend fun getRestaurantList(myLoc: String): List<RestaurantResult>
    suspend fun getKakaoCategoryList(latitude: String, longitude: String): List<KakaoResult>
    suspend fun getTmapRoute(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        endName: String
    ): List<TmapRouteResult>
}