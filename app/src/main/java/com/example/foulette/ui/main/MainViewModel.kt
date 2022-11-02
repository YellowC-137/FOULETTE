package com.example.foulette.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.usecases.getRestaurantListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRestaurantListUseCase: getRestaurantListUseCase,
    //private val saveHistoryUseCase: saveHistoryUseCase
) : ViewModel() {

    private val _restaurantList = MutableStateFlow<List<RestaurantResult>>(emptyList())
    val restaurantList = _restaurantList.asStateFlow()

    fun saveHistory() {

    }

    fun getRestaurant(myLoc: String) {
        viewModelScope.launch {
            _restaurantList.emit(getRestaurantListUseCase.invoke(myLoc))
        }
    }

    fun playRoulette() {

    }

}