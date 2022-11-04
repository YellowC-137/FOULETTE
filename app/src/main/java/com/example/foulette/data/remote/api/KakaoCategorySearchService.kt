package com.example.foulette.data.remote.api

import com.example.foulette.BuildConfig.KAKAO_REST_API_KEY
import com.example.foulette.data.remote.response.kakaoplaces.KakaoSearchResultResponse
import com.example.foulette.util.KAKAO_SEARCH_CATEGORY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoCategorySearchService {
    @GET("${KAKAO_SEARCH_CATEGORY}category.json")
    suspend fun getKakaoCategoryList(
        @Header("Authorization") key: String = KAKAO_REST_API_KEY,
        @Query("x") latitude: String,
        @Query("y") longitude: String,
        @Query("radius") radius: Int = 1000,
        @Query("category_group_code") code: String = "FD6"
    ): Response<KakaoSearchResultResponse>

}