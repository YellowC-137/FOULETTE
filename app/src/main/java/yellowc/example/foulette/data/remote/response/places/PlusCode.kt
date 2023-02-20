package yellowc.example.foulette.data.remote.response.places

import kotlinx.serialization.Serializable

@Serializable
data class PlusCode(
    val compound_code: String,
    val global_code: String
)