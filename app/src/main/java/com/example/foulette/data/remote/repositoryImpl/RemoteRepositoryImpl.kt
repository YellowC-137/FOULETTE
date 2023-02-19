package com.example.foulette.data.remote.repositoryImpl

import com.example.foulette.data.remote.api.JsoupMenuService
import com.example.foulette.data.remote.datasource.RemoteDataSource
import com.example.foulette.data.remote.response.jsoup.JsoupMenuResponse
import com.example.foulette.data.remote.response.places.Result
import com.example.foulette.data.remote.response.tmap.LineString
import com.example.foulette.di.DispatcherModule
import com.example.foulette.domain.models.JsoupMenu
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.models.TmapRouteResult
import com.example.foulette.domain.repositories.RestaurantRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val jsoupMenuService: JsoupMenuService,
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
                    val pic = arrayListOf<String>()
                    if (food.photos != null) {
                        for (p in food.photos) {
                            pic.add(p.photo_reference)
                        }
                    }
                    val temp = RestaurantResult(
                        id = food.place_id,
                        price_level = food.price_level,
                        name = food.name,
                        type = food.types?.get(0),
                        latitude = food.geometry?.location?.lat,
                        longitude = food.geometry?.location?.lng,
                        rate = food.rating,
                        ImgUrl = food.icon,
                        address = food.vicinity,
                        photos = pic
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
        startName: String,
        endName: String
    ): List<TmapRouteResult> {
        val result = ArrayList<TmapRouteResult>()
        val coor = ArrayList<List<String>>()
        withContext(dispatcherIO) {
            val responseListJob = async {
                remoteDataSource.getTmapRoute(startX, startY, endX, endY, startName, endName)
            }
            when (val responseList = responseListJob.await()) {
                is com.example.foulette.domain.models.Result.Success -> {
                    for (test in responseList.data.features) {
                        if (test.geometry.type == "LineString") {
                            val line: LineString = test.geometry as LineString
                            for (route in line.coordinates) {
                                coor.add(route)
                            }
                            val temp = TmapRouteResult(
                                coor,
                                responseList.data.features[0].properties!!.totalDistance!!,
                                responseList.data.features[0].properties!!.totalTime!!
                            )
                            result.add(temp)
                        }
                    }

                }
                is com.example.foulette.domain.models.Result.Error -> {
                    return@withContext
                }
            }
        }

        return result
    }

    override suspend fun getMenu(url: String): List<JsoupMenu> {
        val result = ArrayList<JsoupMenu>()
        withContext(dispatcherIO) {
            val test = jsoupMenuService.getMenu(url)
            Timber.e("테스트 : 리포지토리 사이즈 : ${test.size}")
            /*
            val responseMenuJob = async {
                val a = jsoupMenuService.getMenu(url)
                Timber.e("테스트 : 리포지토리 사이즈 : ${a.size}")
            }
            val menuList = responseMenuJob.await()
            if (menuList.isNotNull()) {
                Timber.e("테스트 : ON")
                for (menu in menuList) {
                    val resultMenu = JsoupMenu(
                        menu_name = menu.menu_name,
                        menu_price = menu.menu_price
                    )
                    result.add(resultMenu)
                }
            } else {
                Timber.e("테스트 : NULL")
                return@withContext
            }
        }
             */
            //return result
        }
        return result

    }
}
