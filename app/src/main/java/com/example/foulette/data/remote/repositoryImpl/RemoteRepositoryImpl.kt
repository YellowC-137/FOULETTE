package com.example.foulette.data.remote.repositoryImpl

import com.example.foulette.data.remote.datasource.RemoteDataSource
import com.example.foulette.data.remote.response.places.Result
import com.example.foulette.di.DispatcherModule
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


    override suspend fun getTmapRoute(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startName: String,
        endName: String
    ): List<TmapRouteResult> {
        val result = ArrayList<TmapRouteResult>()
        withContext(dispatcherIO) {
            val responseListJob = async {
                remoteDataSource.getTmapRoute(startX, startY, endX, endY, startName, endName)
            }
            when (val responseList = responseListJob.await()) {
                is com.example.foulette.domain.models.Result.Success -> {

                }
                is com.example.foulette.domain.models.Result.Error -> {
                    return@withContext
                }
            }
            for (test in result) {
                Timber.e(test.pointX + "," + test.pointY)
            }
        }

        return result
    }


}

//coor = [ [a,b] , [c,d]   ]
