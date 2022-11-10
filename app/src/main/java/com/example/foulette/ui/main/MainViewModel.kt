package com.example.foulette.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.models.TmapRouteResult
import com.example.foulette.domain.usecases.GetRestaurantListUseCase
import com.example.foulette.domain.usecases.GetTmapRouteUseCase
import com.example.foulette.domain.usecases.SaveHistoryUseCase
import com.example.foulette.ui.roulette.RouletteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _rouletteState = MutableSharedFlow<RouletteState>()
    val rouletteState = _rouletteState.asSharedFlow()

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