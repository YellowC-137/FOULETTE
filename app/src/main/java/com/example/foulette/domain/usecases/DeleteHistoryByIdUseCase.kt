package com.example.foulette.domain.usecases

import com.example.foulette.domain.repositories.HistoryRepository
import javax.inject.Inject

class DeleteHistoryByIdUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(id: Int) =
        historyRepository.deleteHistoryById(id)
}