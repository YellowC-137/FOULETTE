package com.example.foulette.data.remote.response.places

import kotlinx.serialization.Serializable

@Serializable
data class OpeningHours(
    val open_now: Boolean
)