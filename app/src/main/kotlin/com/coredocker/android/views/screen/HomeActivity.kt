package com.coredocker.android.views.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.coredocker.android.R
import com.coredocker.android.services.SnackBarNotification
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private val _notifications: SnackBarNotification by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val parentLayout: View = findViewById(R.id.navigation_host_fragment)
        _notifications.messageStream.observe(this, Observer {
            _notifications.shoSnackBar(parentLayout, it)
        })
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.navigation_host_fragment).navigateUp()
}