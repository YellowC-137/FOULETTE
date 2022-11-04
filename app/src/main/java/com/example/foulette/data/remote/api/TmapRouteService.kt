package com.example.foulette.data.remote.api

import com.example.foulette.BuildConfig.TMAP_API
import com.example.foulette.data.remote.response.tmap.TmapRouteResultResponse
import com.example.foulette.util.TMAP_ROUTE
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface TmapRouteService {
    @GET(TMAP_ROUTE+"pedestrian?version=1&callback=function")
    suspend fun getRouteList(
        @Header("accept") key: String = "application/json",
        @Header("content-type") secret: String = "application/json",
        @Header("appKey") appkey: String = TMAP_API,
        @Body body: TmapBody,
    ): Response<TmapRouteResultResponse>

    //val response = client.newCall(request).execute()
}

data class TmapBody(
    val startX: Double,
    val startY: Double,
    val endX: Double,
    val endY: Double,
    val startName: String = "현위치",
    val endName: String
)