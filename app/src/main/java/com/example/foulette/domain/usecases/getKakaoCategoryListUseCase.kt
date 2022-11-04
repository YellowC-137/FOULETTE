package com.example.foulette.domain.usecases

import com.example.foulette.domain.repositories.RestaurantRepository
import javax.inject.Inject

class getKakaoCategoryListUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) {
    suspend operator fun invoke(latitude: String, longitude: String) =
        restaurantRepository.getKakaoCategoryList(latitude, longitude)
}