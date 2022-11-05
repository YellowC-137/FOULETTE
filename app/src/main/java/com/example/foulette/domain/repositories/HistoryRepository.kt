package com.example.foulette.domain.repositories

import androidx.paging.PagingData
import com.example.foulette.data.local.entity.HistoryEntity
import com.example.foulette.domain.models.HistoryResult
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun getAll(): Flow<PagingData<HistoryResult>>
    suspend fun saveHistory(id : Int,
                            restaurantName : String,
                            restaurantImgUrl : String,
                            restaurantAddress : String,
                            restauranLocLat : Double,
                            restauranLocLog : Double,
                            date:String)
    suspend fun deleteHistoryById(id:Int)
    suspend fun deleteAll()
}