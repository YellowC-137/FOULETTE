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
                    restaurantImgUrl = it.restaurantImgUrl,
                    restaurantAddress = it.restaurantAddress,
                    restaurantLocLat = it.restaurantLocLat,
                    restaurantLocLog = it.restaurantLocLog,
                    date = it.date
                )
            )
        }
        return historyResultList
    }
}