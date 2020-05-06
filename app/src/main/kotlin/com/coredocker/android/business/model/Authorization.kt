package com.coredocker.android.business.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Authorization(
    @PrimaryKey
    val id: Int,
    val token: String,
    val userId: String,
    val given_name: String,
    val email: String,
    val name: String,
    val exp: Long,
    val role: List<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Authorization

        if (token != other.token) return false

        return true
    }

     fun expiryDate() : Date {
         return Date(this.exp * 1000)
     }
    fun isExpired() : Boolean {
        return expiryDate() < Date()
    }


    override fun hashCode(): Int {
        return token.hashCode()
    }
}
