package com.coredocker.android.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class TimeUtils {

    companion object {
        fun getDaysBetween(timestampOne: Long, timestampTwo: Long): Long {
            val diffDays = TimeUnit.DAYS.convert(timestampOne - timestampTwo, TimeUnit.MILLISECONDS)
            return Math.abs(diffDays)
        }
    }
}

fun Date.toIsoDateTimeString(): String {
    val df: SimpleDateFormat = isoFormatted()
    return df.format(this)
}

fun String.fromIsoDateString(): Date {
    val df: SimpleDateFormat = isoFormatted()
    return df.parse(this)
}

fun isoFormatted(): SimpleDateFormat {
    val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    val formatting = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    formatting.timeZone = timeZone
    return formatting
}
