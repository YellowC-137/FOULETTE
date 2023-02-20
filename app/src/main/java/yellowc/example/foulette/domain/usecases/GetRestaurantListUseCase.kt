package yellowc.example.foulette.domain.usecases

import yellowc.example.foulette.domain.repositories.RestaurantRepository
import javax.inject.Inject

class GetRestaurantListUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) {
    suspend operator fun invoke(myLoc: String) = restaurantRepository.getRestaurantList(myLoc)
}