package com.example.foulette.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Viewport(
    val northeast: Northeast,
    val southwest: Southwest
)