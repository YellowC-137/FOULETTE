package com.example.foulette.domain.repositories

import com.example.foulette.domain.models.RestaurantResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    suspend fun getRestaurantList(myLoc:String): List<RestaurantResult>
}