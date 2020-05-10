package com.coredocker.android.business.model

import androidx.room.Entity
import java.util.Date

@Entity
class User(
    /**
     * The id of the user.
     */
    id: String,
    /**
     * The name of the user.
     */
    var name: String? = null,
    /**
     * The email of the user.
     */
    var email: String? = null,
    /**
     * The roles of the user.
     */
    var roles: List<String>? = null,

    var image: String =
        "https://en.gravatar.com/avatar/64e1b8d34f425d19e1ee2ea7236d3028.jpeg?d=retro",
    var createDate: Date = Date(),
    var updateDate: Date = Date()
) : BaseModel(id) {

    override fun toString(): String {
        return "User(id='$id', name=$name)"
    }
}