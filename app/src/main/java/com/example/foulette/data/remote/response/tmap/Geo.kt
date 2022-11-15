package com.example.foulette.data.remote.response.tmap

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class Geo {
    abstract val type: String
}

@Serializable
@SerialName("LineString")
data class LineString(
    override val type: String,
    val coordinates: List<List<String>>
) : Geo()

@Serializable
@SerialName("Point")
data class Point(
    override val type: String,
    val coordinates: List<String>
) : Geo()