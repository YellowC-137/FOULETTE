package yellowc.example.foulette.domain.repositories

import yellowc.example.foulette.domain.models.JsoupMenu
import yellowc.example.foulette.domain.models.RestaurantResult
import yellowc.example.foulette.domain.models.TmapRouteResult

interface RestaurantRepository {
    suspend fun getRestaurantList(myLoc: String): List<RestaurantResult>
    suspend fun getTmapRoute(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startName: String,
        endName: String
    ): List<TmapRouteResult>

    suspend fun getMenu(url: String): List<JsoupMenu>
}