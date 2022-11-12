package com.example.foulette.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@kotlinx.serialization.Serializable
@Parcelize
data class RestaurantResult(
    val name: String?,
    val type: String?,
    val latitude: Double?,
    val longitude: Double?,
    val rate: Double?,
    val ImgUrl: String?
): Parcelable