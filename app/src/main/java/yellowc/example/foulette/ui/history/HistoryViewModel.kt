package yellowc.example.foulette.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import yellowc.example.foulette.domain.models.HistoryResult
import yellowc.example.foulette.domain.models.TmapRouteResult
import yellowc.example.foulette.domain.usecases.DeleteHistoryByIdUseCase
import yellowc.example.foulette.domain.usecases.GetAllHistoryUseCase
import yellowc.example.foulette.domain.usecases.GetTmapRouteUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAllHistoryUseCase: GetAllHistoryUseCase,
    private val deleteHistoryByIdUseCase: DeleteHistoryByIdUseCase,
    private val getTmapRouteUseCase: GetTmapRouteUseCase
) : ViewModel() {

    private val _historyData: MutableStateFlow<PagingData<HistoryResult>> =
        MutableStateFlow(PagingData.empty())
    val historyData: StateFlow<PagingData<HistoryResult>> = _historyData.asStateFlow()

    private val _routesData = MutableStateFlow<List<TmapRouteResult>>(emptyList())
    val routesData = _routesData.asStateFlow()

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
            deleteHistoryByIdUseCase.invoke(id)
            getAllHistory()
        }
    }

    fun getRoute(
        startLongitude: Double,
        startLatitude: Double,
        endLongitude: Double,
        endLatitude: Double,
        startName: String,
        endName: String
    ) {
        viewModelScope.launch {
            _routesData.emit(
                getTmapRouteUseCase(
                    startLongitude,
                    startLatitude,
                    endLongitude,
                    endLatitude,
                    startName,
                    endName
                )
            )
            //x,y가 반대임 , x:127~ y:37~
        }
    }
}