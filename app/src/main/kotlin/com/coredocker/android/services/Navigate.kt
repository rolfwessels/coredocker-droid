package com.coredocker.android.services

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import com.coredocker.android.business.model.User
import com.coredocker.android.util.SingleLiveEvent

class Navigate {
    enum class Location {
        HomeActivity, UserFragment, UserCreateUpdateFragment
    }

    private val _currentView: SingleLiveEvent<ToNavData> by lazy {
        SingleLiveEvent<ToNavData>()
    }

    val currentView: LiveData<ToNavData>
        get() {
            return _currentView
        }

    fun toMainView() {
        _currentView.postValue(ToNavData(Location.HomeActivity))
    }

    fun toUserList() {
        _currentView.postValue(ToNavData(Location.UserFragment))
    }

    fun toUserCreateUpdate(user: User? = null) {
        var bundleOf = bundleOf()
        if (user != null) {
            bundleOf = bundleOf(
                "id" to user.id,
                "name" to user.name,
                "email" to user.email,
                "roles" to user.roles!!.toTypedArray()
            )
        }

        _currentView.postValue(ToNavData(Location.UserCreateUpdateFragment, bundleOf))
    }

    class ToNavData(val location: Location, val bundle: Bundle? = null)
}
