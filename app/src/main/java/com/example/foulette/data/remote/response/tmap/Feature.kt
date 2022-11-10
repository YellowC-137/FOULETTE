package com.example.foulette.data.remote.response.tmap

import com.google.gson.annotations.Expose
import kotlinx.serialization.Serializable

@Serializable
data class Feature(
    val geometry: Geometry,
    val properties: Properties? = null,
    val type: String
)