package com.example.foulette.data.repositoryImpl

import com.example.foulette.data.remote.datasource.RestaurantRemoteDataSource
import com.example.foulette.di.DispatcherModule
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.repositories.RestaurantRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
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
            val restaurantList: List<com.example.foulette.data.remote.response.Result>

            when (val responseRestaurantList = responseListJob.await()) {
                is com.example.foulette.domain.models.Result.Success -> {
                    restaurantList = responseRestaurantList.data.results
                }
                is com.example.foulette.domain.models.Result.Error -> {
                    return@withContext
                }
            }


            //TODO  받아올때 모두 받아올지 여기서 random으로 받아올지, 일단은 모두 받아오기
            for (food in restaurantList) {
                launch {

                    val temp = RestaurantResult(
                        id = food.place_id.length,
                        name = food.name,
                        type = food.types[0],
                        latitude = food.geometry.location.lat,
                        longitude = food.geometry.location.lng,
                        rate = food.rating,
                        ImgUrl = food.icon
                    )
                    result.add(temp)
                    Timber.d("가게 이름 :" + food.name)
                }
            }
        }
        return result
    }
}
