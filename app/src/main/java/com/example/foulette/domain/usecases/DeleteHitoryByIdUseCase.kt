package com.example.foulette.domain.usecases

import com.example.foulette.domain.repositories.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteHitoryByIdUseCase@Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(id:Int) =
        historyRepository.deleteHistoryById(id)
}