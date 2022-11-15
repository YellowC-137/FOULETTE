package com.example.foulette.data.remote.response.tmap

import android.graphics.Color
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNull.serializer

@Serializable
data class Feature(
    val geometry: Geo,
    val properties: Properties? = null,
    val type: String
)





