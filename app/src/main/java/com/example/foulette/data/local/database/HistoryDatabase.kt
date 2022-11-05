package com.example.foulette.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foulette.data.local.dao.HistoryDao
import com.example.foulette.data.local.entity.HistoryEntity

@Database(
    entities = [HistoryEntity::class],
    version = 1,
    exportSchema = false
)

abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao():HistoryDao
}