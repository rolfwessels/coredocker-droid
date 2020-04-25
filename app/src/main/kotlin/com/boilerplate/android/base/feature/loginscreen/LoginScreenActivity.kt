package com.boilerplate.android.base.feature.loginscreen

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.boilerplate.android.R
import com.boilerplate.android.base.feature.HomeActivity
import com.boilerplate.android.databinding.LoginActivityBinding
import org.koin.android.ext.android.inject



class LoginScreenActivity : AppCompatActivity() {
    private val _viewModel: LoginScreenViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel.email.value = "admin@admin.com"
        _viewModel.password.value = "admin!"

        DataBindingUtil.setContentView<LoginActivityBinding>(this, R.layout.login_activity).apply {
            this.lifecycleOwner = this@LoginScreenActivity
            this.loginViewModel = _viewModel
        }

        _viewModel.isLoggedIn.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                openHomeView()
            }
        })
    }

    private fun openHomeView() {
        println("Load HomeActivity")
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}