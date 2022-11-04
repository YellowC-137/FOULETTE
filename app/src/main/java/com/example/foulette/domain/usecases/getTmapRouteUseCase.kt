package com.example.foulette.domain.usecases

import com.example.foulette.domain.repositories.RestaurantRepository
import javax.inject.Inject

class getTmapRouteUseCase  @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) {
    suspend operator fun invoke(startX: Double,
                                startY: Double,
                                endX: Double,
                                endY: Double,
                                endName: String) =
        restaurantRepository.getTmapRoute(startX, startY, endX, endY, endName)
}