package yellowc.example.foulette.data.local.datasource

import kotlinx.coroutines.flow.Flow
import yellowc.example.foulette.data.local.entity.HistoryEntity

interface LocalDataSource {
    fun getAll(): Flow<List<HistoryEntity>>
    suspend fun saveHistory(historyEntity: HistoryEntity)
    suspend fun deleteHistoryById(id:Int)
}