package yellowc.example.foulette.domain.usecases

import yellowc.example.foulette.domain.models.HistoryResult
import yellowc.example.foulette.domain.repositories.HistoryRepository
import javax.inject.Inject

class SaveHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {

    suspend operator fun invoke(historyResult: HistoryResult) =
        historyRepository.saveHistory(historyResult)
}