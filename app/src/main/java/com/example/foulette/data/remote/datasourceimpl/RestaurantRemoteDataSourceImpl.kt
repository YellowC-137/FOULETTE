package com.example.foulette.data.remote.datasourceimpl

import com.example.foulette.data.remote.api.RestaurantListService
import com.example.foulette.data.remote.datasource.RestaurantRemoteDataSource
import com.example.foulette.data.remote.response.places.RestaurantListResultResponse
import javax.inject.Inject
import com.example.foulette.domain.models.Result

class RestaurantRemoteDataSourceImpl @Inject constructor(
    private val restaurantListService: RestaurantListService
) : RestaurantRemoteDataSource {
    override suspend fun getRestaurantList(myLoc:String): Result<RestaurantListResultResponse> {
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
}