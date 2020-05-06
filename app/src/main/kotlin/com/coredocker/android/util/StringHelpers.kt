package com.coredocker.android.util

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun buildRandomString(length: Int = 10): String {
    val randomString = (1..length)
        .map { _ -> kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
    return randomString
}