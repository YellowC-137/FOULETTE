package yellowc.example.foulette.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import yellowc.example.foulette.domain.models.RestaurantResult
import yellowc.example.foulette.domain.models.TmapRouteResult
import yellowc.example.foulette.domain.usecases.GetRestaurantListUseCase
import yellowc.example.foulette.domain.usecases.GetTmapRouteUseCase
import yellowc.example.foulette.ui.roulette.RouletteState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRestaurantListUseCase: GetRestaurantListUseCase,
    private val getTmapRouteUseCase: GetTmapRouteUseCase
) : ViewModel() {

    private val _restaurantList = MutableStateFlow<List<RestaurantResult>>(emptyList())
    val restaurantList = _restaurantList.asStateFlow()

    private val _rouletteState = MutableSharedFlow<RouletteState>()
    val rouletteState = _rouletteState.asSharedFlow()

    private val _routesData = MutableStateFlow<List<TmapRouteResult>>(emptyList())
    val routesData = _routesData.asStateFlow()

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
            //x,y??? ????????? , x:127~ y:37~
        }
    }


}