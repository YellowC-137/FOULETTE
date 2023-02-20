package yellowc.example.foulette.domain.mapper

import yellowc.example.foulette.data.local.entity.HistoryEntity
import yellowc.example.foulette.domain.models.HistoryResult

object HistoryMapper {
    fun List<HistoryEntity>.mappingHistory(): List<HistoryResult> {
        val historyResultList = mutableListOf<HistoryResult>()
        this.map {
            historyResultList.add(
                HistoryResult(
                    id = it.id,
                    restaurantName = it.restaurantName,
                    restaurantImg = it.restaurantImg,
                    restaurantAddress = it.restaurantAddress,
                    restaurantLocLat = it.restaurantLocLat,
                    restaurantLocLog = it.restaurantLocLog,
                    date = it.date,
                    rate = it.rate,
                    price = it.price,
                    placeId = it.placeId
                )
            )
        }
        return historyResultList
    }
}