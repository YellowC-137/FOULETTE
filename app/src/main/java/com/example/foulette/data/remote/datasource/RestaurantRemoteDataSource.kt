package com.example.foulette.data.remote.datasource

import com.example.foulette.data.remote.response.RestaurantListResultResponse
import com.google.android.gms.maps.model.LatLng
import com.example.foulette.domain.models.Result

interface RestaurantRemoteDataSource {
    suspend fun getRestaurantList(myLoc:String):Result<RestaurantListResultResponse>
}