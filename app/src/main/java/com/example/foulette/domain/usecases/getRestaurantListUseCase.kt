package com.example.foulette.domain.usecases

import com.example.foulette.domain.repositories.RestaurantRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class getRestaurantListUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) {
    suspend operator fun invoke(myLoc: String) = restaurantRepository.getRestaurantList(myLoc)
}