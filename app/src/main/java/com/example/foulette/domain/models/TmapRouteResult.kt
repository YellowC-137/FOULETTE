package com.example.foulette.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@kotlinx.serialization.Serializable
@Parcelize
data class TmapRouteResult(
    val coordinates : List<List<String>>,
    val totalDistance : Int, // m
    val totalTime : Int // 초
) : Parcelable
