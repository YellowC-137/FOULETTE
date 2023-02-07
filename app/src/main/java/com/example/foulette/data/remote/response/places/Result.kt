package com.example.foulette.data.remote.response.places

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val business_status: String? = null,
    val geometry: Geometry? = null,
    val icon: String? = null,
    val icon_background_color: String? = null,
    val icon_mask_base_uri: String? = null,
    val name: String? = null,
    val opening_hours: OpeningHours? = null,
    val photos: List<Photo>? = null,
    val place_id: String? = null,
    val plus_code: PlusCode? = null,
    val price_level: Int? = null, //가격대 0~4
    val rating: Double? = null, //평점 1~5
    val reference: String? = null,
    val scope: String? = null,
    val types: List<String>? = null,
    val user_ratings_total: Int? = null, //총 리뷰수
    val vicinity: String? = null //주소
)