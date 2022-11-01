package com.example.foulette.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantListResultResponse(
    val html_attributions: List<String>,
    val results: List<Result>,
    val status: String
)