package yellowc.example.foulette.data.remote.response.places

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import javax.annotation.Nullable
import javax.annotation.ParametersAreNonnullByDefault

@Serializable
data class Result(
    val business_status: String? = null,
    val geometry: Geometry? = null,
    val icon: String? = null,
    val icon_background_color: String? = null,
    val icon_mask_base_uri: String? = null,
    val name: String? = null,
    val opening_hours: OpeningHours? = null,
    val photos: List<Photo>? = null,
    val place_id: String? = null,
    val plus_code: PlusCode? = null,
    @Nullable val price_Level: Int? = 3, //가격대 0~4
    val rating: Double? = null, //평점 1~5
    val reference: String? = null,
    val scope: String? = null,
    val types: List<String>? = null,
    val user_ratings_total: Int? = null, //총 리뷰수
    val vicinity: String? = null //주소
)