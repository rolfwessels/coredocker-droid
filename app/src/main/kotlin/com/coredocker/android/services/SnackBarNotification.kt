package com.coredocker.android.services

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.coredocker.android.R
import com.coredocker.android.util.SingleLiveEvent
import com.google.android.material.snackbar.Snackbar

class SnackBarNotification {
    private val _messageStream: SingleLiveEvent<SnackBarMessage> by lazy {
        SingleLiveEvent<SnackBarMessage>()
    }

    class SnackBarMessage(val message: String, val type: Types) {
        enum class Types {
            Success, Info, Failure
        }
    }

    fun push(message: String, type: SnackBarMessage.Types = SnackBarMessage.Types.Info) {
        _messageStream.postValue(SnackBarMessage(message, type))
    }

    fun shoSnackBar(parentLayout: View, it: SnackBarMessage) {

        val snackbar = Snackbar.make(parentLayout, it.message, Snackbar.LENGTH_SHORT)
        val color: Int = when (it.type) {
            SnackBarMessage.Types.Success -> R.color.snack_bar_bg_success
            SnackBarMessage.Types.Failure -> R.color.snack_bar_bg_failure
            else -> R.color.snack_bar_bg_info
        }
        setBackground(snackbar, color)
        snackbar.show()
    }

    private fun setBackground(
        snackbar: Snackbar,
        backgroundTabInspire: Int
    ) {
        val snackBarView: View = snackbar.view

        snackbar.show()
        val bgColor = ContextCompat.getColor(
            snackBarView.context,
            backgroundTabInspire
        )

        snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .setTextColor(
                ContextCompat.getColor(
                    snackBarView.context,
                    R.color.snack_bar_text
                )
            )

        snackBarView.setBackgroundColor(bgColor)
    }

    val messageStream: LiveData<SnackBarMessage>
        get() {
            return _messageStream
        }
}
