package yellowc.example.foulette.domain.usecases

import yellowc.example.foulette.domain.repositories.RestaurantRepository
import javax.inject.Inject

class GetTmapRouteUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) {
    suspend operator fun invoke(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startName: String,
        endName: String
    ) =
        restaurantRepository.getTmapRoute(startX, startY, endX, endY, startName, endName)
}