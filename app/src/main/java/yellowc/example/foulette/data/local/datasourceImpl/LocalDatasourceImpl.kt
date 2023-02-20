package yellowc.example.foulette.data.local.datasourceImpl

import kotlinx.coroutines.flow.Flow
import yellowc.example.foulette.data.local.dao.HistoryDao
import yellowc.example.foulette.data.local.datasource.LocalDataSource
import yellowc.example.foulette.data.local.entity.HistoryEntity
import javax.inject.Inject

class LocalDatasourceImpl @Inject constructor(
    private val historyDao: HistoryDao,
) : LocalDataSource {
    override fun getAll(): Flow<List<HistoryEntity>> = historyDao.getAll()

    override suspend fun saveHistory(historyEntity: HistoryEntity) {
        historyDao.saveHistory(historyEntity)
    }

    override suspend fun deleteHistoryById(id:Int) {
        historyDao.deleteHistoryById(id)
    }
}