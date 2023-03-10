package yellowc.example.foulette.data.remote.datasource

import yellowc.example.foulette.domain.models.Result
import yellowc.example.foulette.data.remote.response.places.RestaurantListResultResponse
import yellowc.example.foulette.data.remote.response.tmap.TmapRouteResultResponse

interface RemoteDataSource {
    suspend fun getRestaurantList(myLoc: String): Result<RestaurantListResultResponse>

    suspend fun getTmapRoute(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startName: String,
        endName: String
    ): Result<TmapRouteResultResponse>

    }
