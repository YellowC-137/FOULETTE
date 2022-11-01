package com.example.foulette.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class OpeningHours(
    val open_now: Boolean
)