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
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val restaurantListService: RestaurantListService,
    private val tmapRouteService: TmapRouteService
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
    /*
    override suspend fun getMenu(url: String): Result<List<JsoupMenuResponse>> {
        val doc = Jsoup.connect(url).get()
        val menuList = mutableListOf<JsoupMenuResponse>()
        val subList = listOf("jnwQZ", "mpoxR")
        //div place_section_content
        //ul ZUYk_
        //li P_Yxm 쭉
        //span zPfVt 가게 설명
        val menuElement: Element? = doc.select("div.place_section_content:has(ul.jnwQZ)").first()
        val subMenu: Elements = menuElement?.select("ul.jnwQZ > li") ?: Elements()
        val nameElements = doc.select("div[class=place_section_content] ul[class=jnwQZ] li a")
        val priceElements = doc.select("div[class=place_section_content] ul[class=jnwQZ] li em")

        for (element in nameElements.indices) {
            val menuName = nameElements[element].text()
            val menuPrice = priceElements[element].text()
            val menu = JsoupMenuResponse(
                menu_name = menuName,
                menu_price = menuPrice
            )
            menuList.add(menu)
        }
        return try {
            if (menuList.size != 0) {
                Result.Success(menuList)
            } else {
                Result.Error(IllegalArgumentException("ERROR"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
*/
}

