package com.example.foulette.domain.usecases

import com.example.foulette.domain.repositories.HistoryRepository
import javax.inject.Inject

class SaveHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(
        id: Int,
        restaurantName: String,
        restaurantImgUrl: String,
        restaurantAddress: String,
        restauranLocLat: Double,
        restauranLocLog: Double,
        date: String
    ) =
        historyRepository.saveHistory(
            id,
            restaurantName,
            restaurantImgUrl,
            restaurantAddress,
            restauranLocLat,
            restauranLocLog,
            date
        )
}