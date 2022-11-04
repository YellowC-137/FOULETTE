package com.example.foulette.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foulette.domain.models.KakaoResult
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.models.TmapRouteResult
import com.example.foulette.domain.usecases.getKakaoCategoryListUseCase
import com.example.foulette.domain.usecases.getRestaurantListUseCase
import com.example.foulette.domain.usecases.getTmapRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRestaurantListUseCase: getRestaurantListUseCase,
    private val getKakaoCategoryListUseCase: getKakaoCategoryListUseCase,
    private val getTmapRouteUseCase: getTmapRouteUseCase
    //private val saveHistoryUseCase: saveHistoryUseCase
) : ViewModel() {

    private val _restaurantList = MutableStateFlow<List<RestaurantResult>>(emptyList())
    val restaurantList = _restaurantList.asStateFlow()

    private val _kakaoList = MutableStateFlow<List<KakaoResult>>(emptyList())
    val kakaoList = _kakaoList.asStateFlow()

    private val _routeList = MutableStateFlow<List<TmapRouteResult>>(emptyList())
    val routeList = _routeList.asStateFlow()


    fun saveHistory() {

    }

    fun getRestaurant(myLoc: String) {
        viewModelScope.launch {
            _restaurantList.emit(getRestaurantListUseCase.invoke(myLoc))
        }
    }

    fun getKakoSearch(latitude: String, longitude: String) {
        viewModelScope.launch {
            _kakaoList.emit(getKakaoCategoryListUseCase.invoke(latitude, longitude))
        }
    }

    fun getRoute(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        endName: String
    ) {
        viewModelScope.launch {
            _routeList.emit(getTmapRouteUseCase.invoke(startX, startY, endX, endY, endName))
        }
    }

}