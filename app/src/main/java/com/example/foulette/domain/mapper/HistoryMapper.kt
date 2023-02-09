package com.example.foulette.domain.mapper

import com.example.foulette.data.local.entity.HistoryEntity
import com.example.foulette.domain.models.HistoryResult

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