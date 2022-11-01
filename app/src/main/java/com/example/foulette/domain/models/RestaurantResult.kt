package com.example.foulette.domain.models

@kotlinx.serialization.Serializable
data class RestaurantResult(
    val id: Int,
    val name: String,
    val type: String,
    val latitude: Double,
    val longitude: Double,
    val rate: Double,
    val ImgUrl: String
)