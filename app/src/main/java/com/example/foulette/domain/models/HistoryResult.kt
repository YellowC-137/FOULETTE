package com.example.foulette.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryResult(
    val id:Int,
    val restaurantName : String,
    val restaurantImgUrl : String,
    val restaurantAddress : String,
    val restaurantLocLat : Double,
    val restaurantLocLog : Double,
    val date:String
):Parcelable