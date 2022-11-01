package com.example.foulette.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Northeast(
    val lat: Double,
    val lng: Double
)