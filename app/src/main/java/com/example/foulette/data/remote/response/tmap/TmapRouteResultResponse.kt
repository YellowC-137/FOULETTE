package com.example.foulette.data.remote.response.tmap

import kotlinx.serialization.Serializable

@Serializable
data class TmapRouteResultResponse(
    val features: List<Feature>,
    val type: String
)