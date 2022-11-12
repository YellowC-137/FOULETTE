package com.example.foulette.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foulette.data.local.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * from history ORDER BY date DESC")
    fun getAll(): Flow<List<HistoryEntity>>
    //Flow는 suspend 할 필요 X

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHistory(historyEntity: HistoryEntity)

    @Query("DELETE FROM history WHERE restaurantName = :name")
    suspend fun deleteHistoryByName(name: String)

    @Query("SELECT * from history ORDER BY date DESC LIMIT :loadSize OFFSET (:page - 1) * :loadSize")
    suspend fun getHistoryList(page: Int, loadSize: Int): List<HistoryEntity>
}