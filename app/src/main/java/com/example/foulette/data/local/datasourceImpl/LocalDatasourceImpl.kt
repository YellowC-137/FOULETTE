package com.example.foulette.data.local.datasourceImpl

import com.example.foulette.data.local.dao.HistoryDao
import com.example.foulette.data.local.datasource.LocalDataSource
import com.example.foulette.data.local.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDatasourceImpl @Inject constructor(
    private val historyDao: HistoryDao,
) : LocalDataSource {
    override fun getAll(): Flow<List<HistoryEntity>> = historyDao.getAll()


    override suspend fun saveHistory(historyEntity: HistoryEntity) {
        historyDao.saveHistory(historyEntity)
    }

    override suspend fun deleteHistoryByName(name:String) {
        historyDao.deleteHistoryByName(name)
    }
}