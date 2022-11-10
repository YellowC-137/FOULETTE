package com.example.foulette.data.remote.response.tmap

import com.google.gson.JsonDeserializationContext
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val coordinates: List<List<String>>?,
    val type: String
)


