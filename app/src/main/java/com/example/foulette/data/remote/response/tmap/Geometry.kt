package com.example.foulette.data.remote.response.tmap

import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val coordinates: List<String>,
    val type: String
)