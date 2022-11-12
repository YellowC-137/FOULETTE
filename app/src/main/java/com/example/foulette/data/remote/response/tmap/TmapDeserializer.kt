package com.example.foulette.data.remote.response.tmap

import com.google.gson.*
import timber.log.Timber
import java.lang.reflect.Type

class TmapDeserializer : JsonDeserializer<TResponses?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): TResponses {
        Timber.tag("Test/Deserializer").d("Using a custom deserializer for the request")
        val gson = Gson()
        val responses: TmapRouteResultResponse =
            gson.fromJson(json, TmapRouteResultResponse::class.java)
        // The full response as a json object
        val jsonObject: JsonObject = json.getAsJsonObject()

        // The response attribute in the JSON received
        val jsonElement: JsonElement = jsonObject.get("response")

        // The response will be parsed because the status was 1
        val tMapRouts: TmapRouteResultResponse = gson.fromJson(
            jsonElement,
            TmapRouteResultResponse::class.java
        )
        val result = arrayListOf<List<List<String>>>()
        val t = TResponses(result)
        Timber.e("${t.LineString.size}")
        return t
        }

    }

