package com.example.foulette.data.remote.response.tmap

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val coordinates: List<List<String>>,
    val type: String
)
