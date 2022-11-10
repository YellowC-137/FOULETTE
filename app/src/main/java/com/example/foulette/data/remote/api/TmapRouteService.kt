package com.example.foulette.data.remote.api

import com.example.foulette.BuildConfig.TMAP_API
import com.example.foulette.data.remote.response.tmap.TmapRouteResultResponse
import com.example.foulette.util.TMAP_ROUTE
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TmapRouteService {
    @POST(TMAP_ROUTE + "pedestrian")
    suspend fun getRouteList(
        @Header("appKey") appkey: String = TMAP_API,
        @Query("version") version: String = "1",
        @Query("callback") callback: String = "function",
        @Body body: TmapBody
    ): Response<TmapRouteResultResponse>
}


@Serializable
data class TmapBody(
    val startX: Double,
    val startY: Double,
    val endX: Double,
    val endY: Double,
    val startName: String,
    val endName: String
)
