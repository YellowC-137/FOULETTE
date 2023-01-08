package com.example.foulette.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.models.TmapRouteResult
import com.example.foulette.domain.usecases.GetTmapRouteUseCase
import com.example.foulette.domain.usecases.SaveHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            saveHistoryUseCase.invoke(historyResult)
        }
    }

}