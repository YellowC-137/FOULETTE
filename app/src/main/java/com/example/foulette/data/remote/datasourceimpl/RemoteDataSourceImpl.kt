package com.example.foulette.data.remote.datasourceimpl

import com.example.foulette.data.remote.api.RestaurantListService
import com.example.foulette.data.remote.api.TmapBody
import com.example.foulette.data.remote.api.TmapRouteService
import com.example.foulette.data.remote.datasource.RemoteDataSource
import com.example.foulette.data.remote.response.places.RestaurantListResultResponse
import com.example.foulette.data.remote.response.tmap.TResponses
import com.example.foulette.data.remote.response.tmap.TmapRouteResultResponse
import com.example.foulette.domain.models.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

//TODO: 수정

class RemoteDataSourceImpl @Inject constructor(
    private val restaurantListService: RestaurantListService,
    private val tmapRouteService: TmapRouteService
) : RemoteDataSource {
    override suspend fun getRestaurantList(myLoc: String): Result<RestaurantListResultResponse> {
        val response = restaurantListService.getRestaurantList(myLoc)
        return try {
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(IllegalArgumentException("ERROR"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


    override suspend fun getTmapRoute(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startName: String,
        endName: String
    ): Result<TmapRouteResultResponse> {
        val tbody = TmapBody(
            startX = startX,
            startY = startY,
            endX = endX,
            endY = endY,
            startName = startName,
            endName = endName
        )
        val response = tmapRouteService.getRouteList(body = tbody) // 수정
        return try {
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(IllegalArgumentException("ERROR"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}

