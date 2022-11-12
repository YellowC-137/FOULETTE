package com.example.foulette.data.local.datasource

import androidx.paging.PagingData
import com.example.foulette.data.local.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAll(): Flow<List<HistoryEntity>>
    suspend fun saveHistory(historyEntity: HistoryEntity)
    suspend fun deleteHistoryByName(name:String)
}