package com.example.foulette.data.remote.response.tmap

import kotlinx.serialization.Serializable

@Serializable
data class Feature(
    val geometry: Geo,
    val properties: Properties? = null,
    val type: String
)
