package com.boilerplate.android.base.feature.launchscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telecom.Call
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.boilerplate.android.R
import com.boilerplate.android.base.feature.HomeActivity
import com.boilerplate.android.base.feature.loginscreen.LoginScreenActivity
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject


class LaunchScreenActivity : AppCompatActivity() {
    private val viewModel: LaunchScreenViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launchscreen_activity)

        GlobalScope.launch(Dispatchers.IO) {
            delay(1000)
            var isLoggedIn = viewModel.isLoggedIn();
            withContext(Dispatchers.Main) {
                if (!isLoggedIn) {
                    openLoginView()
                }
                else {
                    openHomeView()
                }
            }

        }
    }


    private fun openLoginView() {
        val intent = Intent(this, LoginScreenActivity::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val findViewById = findViewById<ImageView>(R.id.imageView)

            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this, android.util.Pair<android.view.View,String>(findViewById,"coredocker_logo")).toBundle())
        }
        else {
            startActivity(intent)
        }
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            finish()
        }

    }

    private fun openHomeView() {
        println("Load HomeActivity")
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
