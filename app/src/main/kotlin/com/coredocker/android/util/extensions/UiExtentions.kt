package com.coredocker.android.util.extensions

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.coredocker.android.services.SnackBarNotification
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


fun View.hideIt() {
    this.visibility = View.GONE
}

fun Context.hideKeyboard() {
    val activity = this as Activity
    val inputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    val currentFocusedView = activity.currentFocus
    currentFocusedView?.let {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocusedView.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}


fun <T> Flow<T>.handleErrors(snackBarNotification: SnackBarNotification): Flow<T> = flow {
    try {
        collect { value -> emit(value) }
    }
    catch (e: CancellationException) {
        Log.w("handleErrors", "Cancel on flow: ${e.message}")
    }
    catch (e: Throwable) {
        Log.w("handleErrors", "Error on flow: ${e.message}")
        snackBarNotification.push(e.message?:"Failure",SnackBarNotification.SnackBarMessage.Types.Failure)
    }
}