package com.example.foulette.data.remote.datasource

import com.example.foulette.data.remote.response.places.RestaurantListResultResponse
import com.example.foulette.domain.models.Result

interface RestaurantRemoteDataSource {
    suspend fun getRestaurantList(myLoc:String):Result<RestaurantListResultResponse>
}