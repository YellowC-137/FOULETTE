package com.example.foulette.domain.usecases

import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.repositories.HistoryRepository
import javax.inject.Inject

class SaveHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {

    suspend operator fun invoke(historyResult: HistoryResult) =
        historyRepository.saveHistory(historyResult)
}