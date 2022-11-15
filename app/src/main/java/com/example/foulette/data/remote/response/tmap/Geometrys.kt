package com.example.foulette.data.remote.response.tmap

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    @Json(name = "coordinates")
    val coordinates: Map<String, Coordinates>,
    @Json(name = "type")
    val type: String
)

@Serializable
sealed class Coordinates() {
    data class point(val Point: List<String>):Coordinates()
    data class line(val LineString: List<List<String>>):Coordinates()
}
