package yellowc.example.foulette.data.remote.datasourceimpl


import yellowc.example.foulette.domain.models.Result
import yellowc.example.foulette.data.remote.api.RestaurantListService
import yellowc.example.foulette.data.remote.api.TmapBody
import yellowc.example.foulette.data.remote.api.TmapRouteService
import yellowc.example.foulette.data.remote.datasource.RemoteDataSource
import yellowc.example.foulette.data.remote.response.places.RestaurantListResultResponse
import yellowc.example.foulette.data.remote.response.tmap.TmapRouteResultResponse
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

}

