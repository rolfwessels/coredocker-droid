package com.coredocker.android.data.database.converter

import androidx.room.TypeConverter
import com.coredocker.android.util.fromIsoDateString
import com.coredocker.android.util.toIsoDateTimeString
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RoomTypeConverter {
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    private var moshi: Moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        if (list == null || list.isEmpty()) {
            return null
        }

        val parameterizedType =
            Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(parameterizedType)

        return adapter.toJson(list)
    }

    @TypeConverter
    fun toList(s: String): List<String>? {
        if (s.isEmpty()) {
            return null
        }

        val parameterizedType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(parameterizedType)

        return adapter.fromJson(s)
    }

    @TypeConverter
    fun fromDate(date: Date): String? {
        return date.toIsoDateTimeString()
    }

    @TypeConverter
    fun toDate(stringDate: String): Date {
        return stringDate.fromIsoDateString()
    }
}
