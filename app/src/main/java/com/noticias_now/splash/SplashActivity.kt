package com.noticias_now.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.ricarlo.common.util.ViewState
import com.noticias_now.R
import com.noticias_now.home.HomeActivity
import com.noticias_now.login.LoginActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.result.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {

                }
                is ViewState.Success -> {
                    when (it.data) {
                        is SplashEvent.LaunchLogin -> {
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                        is SplashEvent.LaunchHome -> {
                            startActivity(Intent(this, HomeActivity::class.java))
                        }
                    }
                    finish()
                }
                is ViewState.Error -> {
                    // TODO review
//                    handlerError(it.error)
                }
            }
        })
    }

}