package yellowc.example.foulette.data.remote.api

import yellowc.example.foulette.BuildConfig.MAPS_API_KEY
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Query
import yellowc.example.foulette.data.remote.response.places.RestaurantListResultResponse
import yellowc.example.foulette.util.SEARCH_NEARBY

interface RestaurantListService {
    @GET("${SEARCH_NEARBY}json")
    suspend fun getRestaurantList(
        @Query("location") location: String,
        @Query("key") key: String = MAPS_API_KEY,
        @Query("type") type:String = "food",
        @Query("radius") radius: Int = 1000,
        @Query("opennow") opennow: Boolean = true,
        @Query("language") language: String = "ko",
        @Query("keyword") keyword:String = "restaurant"
    ): Response<RestaurantListResultResponse>

}