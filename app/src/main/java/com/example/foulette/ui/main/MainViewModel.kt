package com.example.foulette.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.usecases.GetRestaurantListUseCase
import com.example.foulette.domain.usecases.SaveHistoryUseCase
import com.example.foulette.ui.roulette.MODE
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
    private val saveHistoryUseCase: SaveHistoryUseCase
) : ViewModel() {

    private val _restaurantList = MutableStateFlow<List<RestaurantResult>>(emptyList())
    val restaurantList = _restaurantList.asStateFlow()

    private val _rouletteState = MutableSharedFlow<RouletteState>()
    val rouletteState = _rouletteState.asSharedFlow()

    fun getRestaurant(myLoc: String) {
        viewModelScope.launch {
            _restaurantList.emit(getRestaurantListUseCase.invoke(myLoc))
        }
    }

    fun setRouletteState(state: RouletteState) {
        viewModelScope.launch {
            _rouletteState.emit(state)
        }
    }

    fun saveResult(historyResult: HistoryResult) {
        viewModelScope.launch {
            saveHistoryUseCase.invoke(historyResult)
        }
    }

}