package com.example.foulette.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val restaurantName : String,
    val restaurantImgUrl : String,
    val restaurantAddress : String,
    val restauranLocLat : Double,
    val restauranLocLog : Double,
    val date:String
)
