package yellowc.example.foulette.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import yellowc.example.foulette.domain.models.HistoryResult
import yellowc.example.foulette.domain.usecases.SaveHistoryUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
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