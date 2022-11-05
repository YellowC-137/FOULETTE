package com.example.foulette.data.local.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.foulette.data.local.dao.HistoryDao
import com.example.foulette.data.local.paging.HistoryPagingSource
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.repositories.HistoryRepository
import com.example.foulette.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : HistoryRepository {
    override suspend fun getAll(): Flow<PagingData<HistoryResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { HistoryPagingSource(historyDao) }
        ).flow
    }

    override suspend fun saveHistory(
        id: Int,
        restaurantName: String,
        restaurantImgUrl: String,
        restaurantAddress: String,
        restauranLocLat: Double,
        restauranLocLog: Double,
        date: String
    ) {
        historyDao.saveHistory(
            id,
            restaurantName,
            restaurantImgUrl,
            restaurantAddress,
            restauranLocLat,
            restauranLocLog,
            date
        )
    }

    override suspend fun deleteHistoryById(id: Int) {
        historyDao.deleteHistoryById(id)
    }

    override suspend fun deleteAll() {
        historyDao.deleteAll()
    }

}