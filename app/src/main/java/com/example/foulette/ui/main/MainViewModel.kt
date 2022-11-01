package com.example.foulette.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foulette.domain.usecases.getRestaurantListUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRestaurantListUseCase: getRestaurantListUseCase,
    //private val saveHistoryUseCase: saveHistoryUseCase
) : ViewModel() {

    fun saveHistory() {

    }

    fun getRestaurant(myLoc: String) {
        viewModelScope.launch {
            getRestaurantListUseCase.invoke(myLoc)
        }
    }

}