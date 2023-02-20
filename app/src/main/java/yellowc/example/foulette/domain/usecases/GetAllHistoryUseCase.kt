package yellowc.example.foulette.domain.usecases


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import yellowc.example.foulette.domain.repositories.HistoryRepository
import javax.inject.Inject

class GetAllHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke() =
        historyRepository.getHistoryList().flowOn(Dispatchers.Default)
}