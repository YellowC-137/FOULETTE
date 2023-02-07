package com.example.foulette.domain.models

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryResult(
    val id: Int,
    val restaurantName: String,
    val restaurantImg: Bitmap,
    val restaurantAddress: String,
    val restaurantLocLat: Double,
    val restaurantLocLog: Double,
    val date: String,
    val rate: Double,
    val price: Int
) : Parcelable