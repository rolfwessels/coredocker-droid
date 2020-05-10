package com.coredocker.android.data.database.converter

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContainAll
import org.junit.Test

import java.util.Date

class RoomTypeConverterTest {

    @Test
    fun fromList_givenList_shouldMakeCommaSeperatedString() {
        // arrange
        val input = listOf("One", "Two")
        // action
        val stringList = RoomTypeConverter().fromList(input)
        // assert
        stringList shouldBeEqualTo "[\"One\",\"Two\"]"
    }

    @Test
    fun toList_givenListString_shouldMatchList() {
        // arrange
        val expected = listOf("One", "Two")
        // action
        val list = RoomTypeConverter().toList("[\"One\",\"Two\"]")
        // assert
        list!!.shouldContainAll(expected)
    }

    @Test
    fun fromDate_givenEncodingAndDecoding_shouldMatch() {
        // arrange
        val roomTypeConverter = RoomTypeConverter()
        val stringDate = "1971-03-04T01:02:03.321Z"

        // action
        val toDate = RoomTypeConverter().toDate(stringDate)
        val fromDate = roomTypeConverter.fromDate(toDate)
        // assert
        fromDate shouldBeEqualTo stringDate
    }

    @Test
    fun fromDate_givenValidDateString_shouldBuildDate() {
        // arrange
        val date = Date(123321)
        // action
        val toDate = RoomTypeConverter().toDate("1970-01-01T00:02:03.321Z")
        // assert
        toDate.toString() shouldBeEqualTo date.toString()
    }

    @Test
    fun toDate_givenValidDate_shouldBuildDateString() {
        // arrange
        val date = Date(123321)
        // action
        val fromDate = RoomTypeConverter().fromDate(date)
        // assert
        fromDate shouldBeEqualTo "1970-01-01T00:02:03.321Z"
    }
}