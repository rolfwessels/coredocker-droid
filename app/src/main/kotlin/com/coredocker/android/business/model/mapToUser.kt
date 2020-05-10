package com.coredocker.android.business.model

import com.coredocker.fragment.UserDto

fun UserDto.mapToUser(): User {
    return User(
        id = this.id,
        name = this.name,
        email = this.email,
        roles = this.roles,
        image = this.image,
        createDate = this.createDate,
        updateDate = this.updateDate
    )
}