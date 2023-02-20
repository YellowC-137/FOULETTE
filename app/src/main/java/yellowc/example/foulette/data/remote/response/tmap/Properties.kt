package yellowc.example.foulette.data.remote.response.tmap

import kotlinx.serialization.Serializable

@Serializable
data class Properties(
    val categoryRoadType: Int? = null,
    val description: String? = null,
    val direction: String? = null,
    val distance: Int? = null,
    val facilityName: String? = null,
    val facilityType: String? = null,
    val index: Int? = null,
    val intersectionName: String? = null,
    val lineIndex: Int? = null,
    val name: String? = null,
    val nearPoiName: String? = null,
    val nearPoiX: String? = null,
    val nearPoiY: String? = null,
    val pointIndex: Int? = null,
    val pointType: String? = null,
    val roadType: Int? = null,
    val time: Int? = null,
    val totalDistance: Int? = null,
    val totalTime: Int? = null,
    val turnType: Int? = null
)