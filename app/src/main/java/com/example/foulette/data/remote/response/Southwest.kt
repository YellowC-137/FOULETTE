package com.example.foulette.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Southwest(
    val lat: Double,
    val lng: Double
)