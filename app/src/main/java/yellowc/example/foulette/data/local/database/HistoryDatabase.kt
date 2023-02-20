package yellowc.example.foulette.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import yellowc.example.foulette.data.local.dao.HistoryDao
import yellowc.example.foulette.data.local.entity.Converters
import yellowc.example.foulette.data.local.entity.HistoryEntity

@Database(
    entities = [HistoryEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}