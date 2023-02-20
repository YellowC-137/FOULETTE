package com.example.foulette.data.local.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.foulette.data.local.dao.HistoryDao
import com.example.foulette.data.local.datasource.LocalDataSource
import com.example.foulette.data.local.entity.toEntity
import com.example.foulette.data.local.paging.HistoryPagingSource
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.repositories.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    private val localDataSource: LocalDataSource
) : HistoryRepository {
    override fun getAll(): Flow<List<HistoryResult>> {
        return localDataSource.getAll().map { it ->
            it.map {
                it.toModel()
            }
        }
    }

    override suspend fun saveHistory(historyResult: HistoryResult) {
        localDataSource.saveHistory(historyResult.toEntity())
    }
    override suspend fun deleteHistoryById(id: Int) {
        localDataSource.deleteHistoryById(id)
    }
    override suspend fun getHistoryList(): Flow<PagingData<HistoryResult>> {
        return (Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HistoryPagingSource(historyDao) }
        ).flow).map { historyList ->
            historyList.map {
                it.toModel()
            }
        }
    }
}