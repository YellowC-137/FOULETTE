package com.example.foulette.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.models.TmapRouteResult
import com.example.foulette.domain.usecases.GetRestaurantListUseCase
import com.example.foulette.domain.usecases.GetTmapRouteUseCase
import com.example.foulette.domain.usecases.SaveHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRestaurantListUseCase: GetRestaurantListUseCase,
    private val getTmapRouteUseCase: GetTmapRouteUseCase,
    private val saveHistoryUseCase: SaveHistoryUseCase
) : ViewModel() {

    private val _restaurantList = MutableStateFlow<List<RestaurantResult>>(emptyList())
    val restaurantList = _restaurantList.asStateFlow()

    private val _routeList = MutableStateFlow<List<TmapRouteResult>>(emptyList())
    val routeList = _routeList.asStateFlow()


    fun saveHistory(
        id: Int,
        restaurantName: String,
        restaurantImgUrl: String,
        restaurantAddress: String,
        restauranLocLat: Double,
        restauranLocLog: Double,
        date: String
    ) {
        viewModelScope.launch {
            saveHistoryUseCase.invoke(
                id,
                restaurantName,
                restaurantImgUrl,
                restaurantAddress,
                restauranLocLat,
                restauranLocLog,
                date
            )

        }
    }

    fun getRestaurant(myLoc: String) {
        viewModelScope.launch {
            _restaurantList.emit(getRestaurantListUseCase.invoke(myLoc))
        }
    }

    fun getRoute(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startName: String,
        endName: String
    ) {
        viewModelScope.launch {
            _routeList.emit(
                getTmapRouteUseCase.invoke(
                    startX,
                    startY,
                    endX,
                    endY,
                    startName,
                    endName
                )
            )
        }
    }

}