package com.example
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalDate

class LocalDateAdapter : TypeAdapter<LocalDate>() {
    override fun write(out: JsonWriter, value: LocalDate?) {
        out.value(value?.toString()) // Convierte LocalDate a String (ISO-8601)
    }

    override fun read(`in`: JsonReader): LocalDate {
        return LocalDate.parse(`in`.nextString()) // Convierte String a LocalDate
    }
}
