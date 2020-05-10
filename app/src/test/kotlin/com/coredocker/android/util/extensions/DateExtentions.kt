package com.coredocker.android.util.extensions

import com.coredocker.android.util.toIsoDateTimeString
import org.hamcrest.MatcherAssert
import java.util.Date

fun Date.shouldBeCloseTo(date: Date, differenceAllowed: Int = 1000) {
    val abs = Math.abs(this.time - date.time)
    MatcherAssert.assertThat(
        "Expected `${this.toIsoDateTimeString()}` date to be close " +
                "to `${date.toIsoDateTimeString()}` but they differ with $abs ms !",
        abs < differenceAllowed
    )
}
