package com.example.foulette.data.remote.response.places

import kotlinx.serialization.Serializable

@Serializable
data class Northeast(
    val lat: Double,
    val lng: Double
)