package com.example.marsheroly.data.api.custom_adapters

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class CustomDateAdapter : JsonAdapter<Date>() {

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return if (reader.peek() != JsonReader.Token.NULL) {
            val dateAsString = reader.nextString()
            val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("GMT")
            dateFormat.parse(dateAsString)
        } else {
            reader.nextNull<Unit>()
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("GMT")
            writer.value(dateFormat.format(value))
        } else {
            writer.nullValue()
        }
    }

    companion object {
        const val SERVER_FORMAT = ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // define your server format here
    }
}