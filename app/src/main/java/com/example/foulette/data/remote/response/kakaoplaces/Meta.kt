package com.example.foulette.data.remote.response.kakaoplaces

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val same_name: String? = null,
    val total_count: Int
)