package com.example.foulette.data.remote.response.kakaoplaces

import kotlinx.serialization.Serializable

@Serializable
data class Document(
    val address_name: String,
    val category_group_code: String,
    val category_group_name: String,
    val category_name: String,
    val distance: String?=null,
    val id: String,
    val phone: String?=null,
    val place_name: String,
    val place_url: String?=null,
    val road_address_name: String,
    val x: String,
    val y: String
)