package com.coredocker.android.util

import org.hamcrest.Matchers
import org.junit.Assert.assertThat
import org.junit.Test

class StringHelpersTest {

    @Test
    fun buildRandomString_GivenLength_ShouldReturnRandomString() {
        val buildRandomString = buildRandomString(10)
        assertThat(buildRandomString.length, Matchers.equalTo(10))
    }
}