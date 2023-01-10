package com.example.foulette.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foulette.domain.models.HistoryResult

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val restaurantName: String,
    val restaurantImgUrl: String,
    val restaurantAddress: String,
    val restaurantLocLat: Double,
    val restaurantLocLog: Double,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val date: String
) {
    fun toModel(): HistoryResult {
        return HistoryResult(
           id =  id,
           restaurantName= restaurantName,
          restaurantImgUrl =   restaurantImgUrl,
          restaurantAddress =   restaurantAddress,
          restaurantLocLat =  restaurantLocLat,
          restaurantLocLog =   restaurantLocLog,
          date =   date
        )
    }
}

fun HistoryResult.toEntity(): HistoryEntity {
    return HistoryEntity(
        id =  id,
        restaurantName= restaurantName,
        restaurantImgUrl =   restaurantImgUrl,
        restaurantAddress =   restaurantAddress,
        restaurantLocLat =  restaurantLocLat,
        restaurantLocLog =   restaurantLocLog,
        date =   date
    )
}