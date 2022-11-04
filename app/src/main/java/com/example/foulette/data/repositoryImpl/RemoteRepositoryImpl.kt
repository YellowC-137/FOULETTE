package com.example.foulette.data.repositoryImpl

import com.example.foulette.data.remote.datasource.RemoteDataSource
import com.example.foulette.data.remote.response.kakaoplaces.Document
import com.example.foulette.data.remote.response.places.Result
import com.example.foulette.data.remote.response.tmap.Feature
import com.example.foulette.data.remote.response.tmap.Geometry
import com.example.foulette.data.remote.response.tmap.TmapRouteResultResponse
import com.example.foulette.di.DispatcherModule
import com.example.foulette.domain.models.KakaoResult
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.models.TmapRouteResult
import com.example.foulette.domain.repositories.RestaurantRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @DispatcherModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : RestaurantRepository {
    override suspend fun getRestaurantList(myLoc: String): List<RestaurantResult> {
        val result = ArrayList<RestaurantResult>()
        withContext(dispatcherIO) {
            val responseListJob = async {
                remoteDataSource.getRestaurantList(myLoc)
            }
            val restaurantList: List<Result>

            when (val responseRestaurantList = responseListJob.await()) {
                is com.example.foulette.domain.models.Result.Success -> {
                    restaurantList = responseRestaurantList.data.results
                }
                is com.example.foulette.domain.models.Result.Error -> {
                    return@withContext
                }
            }

            for (food in restaurantList) {
                launch {
                    val temp = RestaurantResult(
                        id = food.place_id?.length,
                        name = food.name,
                        type = food.types?.get(0),
                        latitude = food.geometry?.location?.lat,
                        longitude = food.geometry?.location?.lng,
                        rate = food.rating,
                        ImgUrl = food.icon
                    )
                    result.add(temp)
                }

            }
        }
        return result
    }

    override suspend fun getKakaoCategoryList(
        latitude: String,
        longitude: String
    ): List<KakaoResult> {
        val result = ArrayList<KakaoResult>()
        withContext(dispatcherIO) {
            val responseListJob = async {
                remoteDataSource.getKakaoCategoryList(latitude, longitude)
            }
            val searchedList: List<Document>

            when (val responseList = responseListJob.await()) {
                is com.example.foulette.domain.models.Result.Success -> {
                    searchedList = responseList.data.documents
                }
                is com.example.foulette.domain.models.Result.Error -> {
                    return@withContext
                }
            }

            for (food in searchedList) {
                launch {
                    val temp = KakaoResult(
                        id = food.id,
                        x = food.x,
                        y = food.y,
                        distance = food.distance,
                        address_name = food.address_name,
                        category_name = food.category_name,
                        phone = food.phone,
                        place_name = food.place_name,
                        place_url = food.place_url,
                        road_address_name = food.road_address_name
                    )
                    result.add(temp)
                }

            }
        }
        return result
    }

    override suspend fun getTmapRoute(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        endName: String
    ): List<TmapRouteResult> {
        val result = ArrayList<TmapRouteResult>()
        withContext(dispatcherIO) {
            val responseListJob = async {
                remoteDataSource.getTmapRoute(startX, startY, endX, endY, endName)
            }
            val routeList: List<Feature>

            when (val responseList = responseListJob.await()) {
                is com.example.foulette.domain.models.Result.Success -> {
                    routeList = responseList.data.features
                }
                is com.example.foulette.domain.models.Result.Error -> {
                    return@withContext
                }
            }

            for (roads in routeList){
                val temp = TmapRouteResult(roads.geometry.coordinates[0],roads.geometry.coordinates[1])
                result.add(temp)
            }
        }

        return result
    }

}
