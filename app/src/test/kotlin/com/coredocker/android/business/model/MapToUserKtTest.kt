package com.coredocker.android.business.model

import com.coredocker.android.util.Randomizer
import com.coredocker.android.util.extensions.shouldBeCloseTo
import com.coredocker.fragment.UserDto
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class MapToUserKtTest {
    private val userFragment: UserDto by lazy { buildUserDto() }

    @Test
    fun mapToUser_WhenCalled_ShouldMapProperties() {
        // action
        val user: User = userFragment.mapToUser()
        // assert
        // todo: we should be able to automate this.
        user.name shouldBeEqualTo userFragment.name
        user.id shouldBeEqualTo userFragment.id
        user.email shouldBeEqualTo userFragment.email
        user.image shouldBeEqualTo userFragment.image
        user.roles shouldBeEqualTo userFragment.roles
    }

    @Test
    fun mapToUser_WhenCalled_ShouldMapDateProperties() {
        // action
        val user: User = userFragment.mapToUser()
        // assert
        user.createDate.shouldBeCloseTo(userFragment.createDate)
        user.updateDate.shouldBeCloseTo(userFragment.updateDate)
    }

    private fun buildUserDto(): UserDto {
        return Randomizer.instance.nextObject(
            UserDto::class.java
        )
    }
}