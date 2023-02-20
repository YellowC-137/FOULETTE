package yellowc.example.foulette.data.remote.response.places

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val height: Int,
    val html_attributions: List<String>,
    val photo_reference: String,
    val width: Int
)