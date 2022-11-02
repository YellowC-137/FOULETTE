package com.example.foulette.data.repositoryImpl

import com.example.foulette.data.remote.datasource.RestaurantRemoteDataSource
import com.example.foulette.data.remote.response.places.Result
import com.example.foulette.di.DispatcherModule
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.repositories.RestaurantRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantRemoteDataSource: RestaurantRemoteDataSource,
    @DispatcherModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : RestaurantRepository {
    override suspend fun getRestaurantList(myLoc: String): List<RestaurantResult> {
        val result = ArrayList<RestaurantResult>()
        withContext(dispatcherIO) {
            val responseListJob = async {
                restaurantRemoteDataSource.getRestaurantList(myLoc)
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
}
