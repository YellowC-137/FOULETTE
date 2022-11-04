package com.example.foulette.data.remote.response.kakaoplaces

import kotlinx.serialization.Serializable

@Serializable
data class KakaoSearchResultResponse(
    val documents: List<Document>,
    val meta: Meta
)