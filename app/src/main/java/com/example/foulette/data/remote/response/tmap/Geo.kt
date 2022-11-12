package com.example.foulette.data.remote.response.tmap

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

//TODO 수정

@Serializable
sealed class Geo(
    @Json(name="type")
    val type: CoordiantesType
)


data class LineString(
    val coordinates: List<List<String>>
) : Geo (CoordiantesType.LineString)


data class Point(
    val coordinates: List<String>
) : Geo (CoordiantesType.Point)



enum class CoordiantesType {
    @Json(name = "LineString")
    LineString,
    @Json(name = "Point")
    Point
}