package com.coredocker.android.views.screen.launchscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.coredocker.android.R
import com.coredocker.android.util.extensions.runOnBackground
import com.coredocker.android.views.screen.HomeActivity
import com.coredocker.android.views.screen.loginscreen.LoginScreenActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class LaunchScreenActivity : AppCompatActivity() {
    private val viewModel: LaunchScreenViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launchscreen_activity)

        runOnBackground {
            delay(1000)
            val isLoggedIn = viewModel.isLoggedIn()
            withContext(Main) {
                if (!isLoggedIn) {
                    openLoginView()
                } else {
                    openHomeView()
                }
            }
        }
    }

    private fun openLoginView() {
        val intent = Intent(this, LoginScreenActivity::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val findViewById = findViewById<ImageView>(R.id.imageView)
            startActivity(
                intent,
                ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    android.util.Pair(findViewById, "coredocker_logo")
                ).toBundle()
            )
        } else {
            startActivity(intent)
        }
        // need a way to wait for the animation to finish before we close this
        runOnBackground {
            withContext(Main) {
                finish()
            }
        }
    }

    private fun openHomeView() {
        println("Load HomeActivity")
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
