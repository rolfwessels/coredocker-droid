package com.coredocker.android.util

class LoadingState(
    val isLoading: Boolean = false,
    val messages: Map<String, String> = emptyMap(),
    val name: String = "poes"

) {
    val isValid: Boolean
        get() = messages.isEmpty()

    override fun toString(): String {
        return "LoadingState(isLoading=$isLoading, messages=$messages)"
    }

}