package yellowc.example.foulette.domain.usecases

import yellowc.example.foulette.domain.repositories.RestaurantRepository
import javax.inject.Inject


class GetMenuUseCase @Inject constructor(private val menuRepository: RestaurantRepository) {
    suspend operator fun invoke(url: String) = menuRepository.getMenu(url)

}