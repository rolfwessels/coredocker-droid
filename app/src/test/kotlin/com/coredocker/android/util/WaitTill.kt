package com.coredocker.android.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun <E> MutableList<E>.waitTill(
    timeoutMs: Int = 5000,
    function: (value: MutableList<E>) -> Boolean
) {
    var timeoutAt = System.currentTimeMillis() + timeoutMs
    val mutableList = this
    runBlocking {
        while (timeoutAt > System.currentTimeMillis())
            if (function(mutableList)) {
                break
            }
        delay(10)
    }
}