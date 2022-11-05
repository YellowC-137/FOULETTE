package com.example.foulette.domain.usecases

import com.example.foulette.domain.repositories.HistoryRepository
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke() =
        historyRepository.deleteAll()
}