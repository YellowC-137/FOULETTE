package com.example.foulette.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.usecases.GetRestaurantListUseCase
import com.example.foulette.domain.usecases.GetTmapRouteUseCase
import com.example.foulette.domain.usecases.SaveHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getTmapRouteUseCase: GetTmapRouteUseCase,
    private val saveHistoryUseCase: SaveHistoryUseCase
) : ViewModel() {

    fun saveHistory(
        historyResult: HistoryResult
    ) {
        viewModelScope.launch {
            saveHistoryUseCase.invoke(historyResult) }
    }

    fun getRoute(startX: Double,
                 startY: Double,
                 endX: Double,
                 endY: Double,
                 startName: String,
                 endName: String){
        viewModelScope.launch {
            getTmapRouteUseCase(startX, startY, endX, endY, startName, endName)
            //x,y가 반대임 , x:127~ y:37~
        }
    }

}