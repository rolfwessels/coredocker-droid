package com.coredocker.android.views.screen.loginscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.coredocker.android.R
import com.coredocker.android.databinding.LoginActivityBinding
import com.coredocker.android.services.Navigate
import com.coredocker.android.views.screen.HomeActivity
import org.koin.android.ext.android.inject

class LoginScreenActivity : AppCompatActivity() {
    private val _viewModel: LoginScreenViewModel by inject()
    private val _navigate: Navigate by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel.email.value = "admin@admin.com"
        _viewModel.password.value = "admin!"

        DataBindingUtil.setContentView<LoginActivityBinding>(this, R.layout.login_activity).apply {
            this.lifecycleOwner = this@LoginScreenActivity
            this.loginViewModel = _viewModel
        }
        _navigate.currentView.observe(this, Observer { n ->
            when (n.location) {
                Navigate.Location.HomeActivity -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> throw ArrayIndexOutOfBoundsException("Unknown nav location ${n.location}")
            }
        })
    }
}