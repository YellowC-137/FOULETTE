package com.example.foulette.data.remote.api

import com.example.foulette.data.remote.response.jsoup.JsoupMenuResponse

interface JsoupMenuService {
    suspend fun getMenu(url:String):List<JsoupMenuResponse>
}