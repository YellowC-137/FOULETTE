package yellowc.example.foulette.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import yellowc.example.foulette.domain.models.HistoryResult

interface HistoryRepository {
    fun getAll(): Flow<List<HistoryResult>>
    suspend fun saveHistory(historyResult: HistoryResult)
    suspend fun deleteHistoryById(id: Int)
    suspend fun getHistoryList(): Flow<PagingData<HistoryResult>>

}