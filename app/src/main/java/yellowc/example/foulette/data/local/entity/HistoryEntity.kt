package yellowc.example.foulette.data.local.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import yellowc.example.foulette.domain.models.HistoryResult

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val restaurantName: String,
    val restaurantImg: Bitmap,
    val restaurantAddress: String,
    val restaurantLocLat: Double,
    val restaurantLocLog: Double,
    val price: Int,
    val rate: Double,
    val placeId:String,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val date: String
) {
    fun toModel(): HistoryResult {
        return HistoryResult(
            id = id,
            restaurantName = restaurantName,
            restaurantImg = restaurantImg,
            restaurantAddress = restaurantAddress,
            restaurantLocLat = restaurantLocLat,
            restaurantLocLog = restaurantLocLog,
            date = date,
            price = price,
            rate = rate,
            placeId = placeId
        )
    }
}

fun HistoryResult.toEntity(): HistoryEntity {
    return HistoryEntity(
        id = id,
        restaurantName = restaurantName,
        restaurantImg = restaurantImg,
        restaurantAddress = restaurantAddress,
        restaurantLocLat = restaurantLocLat,
        restaurantLocLog = restaurantLocLog,
        date = date,
        price = price,
        rate = rate,
        placeId = placeId
    )
}