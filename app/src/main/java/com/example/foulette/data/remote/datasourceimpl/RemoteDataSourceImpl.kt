package com.example.foulette.data.remote.datasourceimpl

import com.example.foulette.data.remote.api.JsoupMenuService
import com.example.foulette.data.remote.api.RestaurantListService
import com.example.foulette.data.remote.api.TmapBody
import com.example.foulette.data.remote.api.TmapRouteService
import com.example.foulette.data.remote.datasource.RemoteDataSource
import com.example.foulette.data.remote.response.jsoup.JsoupMenuResponse
import com.example.foulette.data.remote.response.places.RestaurantListResultResponse
import com.example.foulette.data.remote.response.tmap.TmapRouteResultResponse
import com.example.foulette.domain.models.Result
import org.jsoup.Jsoup
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val restaurantListService: RestaurantListService,
    private val tmapRouteService: TmapRouteService,
    private val jsoupMenuService: JsoupMenuService
) : RemoteDataSource {
    override suspend fun getRestaurantList(myLoc: String): Result<RestaurantListResultResponse> {
        val response = restaurantListService.getRestaurantList(myLoc)
        return try {
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(IllegalArgumentException("ERROR"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


    override suspend fun getTmapRoute(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startName: String,
        endName: String
    ): Result<TmapRouteResultResponse> {
        val tbody = TmapBody(
            startX = startX,
            startY = startY,
            endX = endX,
            endY = endY,
            startName = startName,
            endName = endName
        )
        val response = tmapRouteService.getRouteList(body = tbody)
        return try {
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(IllegalArgumentException("ERROR"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getMenu(url: String): Result<List<JsoupMenuResponse>> {
        val response = jsoupMenuService.getMenu(url)
        val doc = Jsoup.connect(url).get()
        val menuList = mutableListOf<JsoupMenuResponse>()
        //div place_section_content
        //ul ZUYk_
        //li P_Yxm 쭉
        //span zPfVt 가게 설명
        val menuElement = doc.select("")
        for (element in menuElement) {
            val menuName = element.select("").text()
            val menuPrice = element.select("").text()
            val menuPic = element.select("")
            val menuDescription = element.select("").text()
            val menu = JsoupMenuResponse(
                menu_name = menuName,
                menu_description = menuDescription,
                menu_pic = menuPic.toString(),
                menu_price = menuPrice
            )
            menuList.add(menu)
        }
        return try {
            if (response.isNotEmpty()) {
                Result.Success(menuList)
            } else {
                Result.Error(IllegalArgumentException("ERROR"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}

