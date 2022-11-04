package com.example.foulette.domain.models

@kotlinx.serialization.Serializable
data class KakaoResult(
    val address_name: String,
    val category_name: String,
    val distance: String?,
    val id: String,
    val phone: String?,
    val place_name: String,
    val place_url: String?,
    val road_address_name: String,
    val x: String,
    val y: String
)
