package com.example.foulette.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val location: Location,
    val viewport: Viewport
)