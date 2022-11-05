package com.example.foulette.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foulette.data.local.entity.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * from history ORDER BY date DESC")
    suspend fun getAll(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveHistory(id : Int,
                    restaurantName : String,
                    restaurantImgUrl : String,
                    restaurantAddress : String,
                    restauranLocLat : Double,
                    restauranLocLog : Double,
                    date:String)

    @Query("DELETE FROM history WHERE id = :id")
    suspend fun deleteHistoryById(id:Int)

    @Query("DELETE FROM history")
    fun deleteAll()


}