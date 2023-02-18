package com.example.foulette.domain.usecases


import com.example.foulette.domain.models.JsoupMenu
import com.example.foulette.domain.repositories.RestaurantRepository
import javax.inject.Inject


class GetMenuUseCase @Inject constructor(private val menuRepository: RestaurantRepository) {
    suspend operator fun invoke(url: String) = menuRepository.getMenu(url)

}