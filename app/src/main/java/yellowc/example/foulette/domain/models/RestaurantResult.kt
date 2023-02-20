package yellowc.example.foulette.domain.models

import android.os.Parcelable
import com.google.android.libraries.places.api.model.OpeningHours
import kotlinx.parcelize.Parcelize

@kotlinx.serialization.Serializable
@Parcelize
data class RestaurantResult(
    val id:String?,
    val price_level:Int?,
    val name: String?,
    val type: String?,
    val latitude: Double?,
    val longitude: Double?,
    val rate: Double?,
    val ImgUrl: String?,
    val address:String?,
    val photos : List<String>?
): Parcelable