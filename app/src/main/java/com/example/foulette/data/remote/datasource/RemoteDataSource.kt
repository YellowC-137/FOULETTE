package com.example.foulette.data.remote.datasource

import com.example.foulette.data.remote.response.jsoup.JsoupMenuResponse
import com.example.foulette.data.remote.response.places.RestaurantListResultResponse
import com.example.foulette.data.remote.response.tmap.TmapRouteResultResponse
import com.example.foulette.domain.models.Result

interface RemoteDataSource {
    suspend fun getRestaurantList(myLoc: String): Result<RestaurantListResultResponse>

    suspend fun getTmapRoute(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startName: String,
        endName: String
    ): Result<TmapRouteResultResponse>

    suspend fun getMenu(
        url: String
    ): Result<List<JsoupMenuResponse>>
}