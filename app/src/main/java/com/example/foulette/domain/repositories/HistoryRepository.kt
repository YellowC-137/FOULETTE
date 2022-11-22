package com.example.foulette.domain.repositories

import androidx.paging.PagingData
import com.example.foulette.domain.models.HistoryResult
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getAll(): Flow<List<HistoryResult>>
    suspend fun saveHistory(historyResult: HistoryResult)
    suspend fun deleteHistoryById(id: Int)
    suspend fun getHistoryList(): Flow<PagingData<HistoryResult>>

}