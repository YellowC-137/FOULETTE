package com.example.foulette.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.usecases.DeleteHistoryUseCase
import com.example.foulette.domain.usecases.DeleteHitoryByIdUseCase
import com.example.foulette.domain.usecases.GetAllHistoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val getAllHistoryUseCase: GetAllHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase,
    private val deleteHitoryByIdUseCase: DeleteHitoryByIdUseCase
) : ViewModel() {

    private val _historyData: MutableStateFlow<PagingData<HistoryResult>> =
        MutableStateFlow(PagingData.empty())
    val historyData: StateFlow<PagingData<HistoryResult>> = _historyData.asStateFlow()

    fun getAllHistory() {
        viewModelScope.launch {
            getAllHistoryUseCase.invoke()
                .cachedIn(viewModelScope)
                .collectLatest {
                    _historyData.emit(it)
                }
        }
    }

    fun deleteHistoryById(id: Int) {
        viewModelScope.launch {
                deleteHitoryByIdUseCase.invoke(id)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
                deleteHistoryUseCase.invoke()
        }
    }
}