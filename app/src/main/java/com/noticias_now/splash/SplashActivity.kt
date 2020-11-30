package com.noticias_now.splash

import android.os.Bundle
import br.com.ricarlo.common.ui.base.BaseActivity
import br.com.ricarlo.common.util.ViewState
import com.noticias_now.R
import com.noticias_now.databinding.ActivitySplashBinding
import com.noticias_now.home.HomeActivity
import com.noticias_now.login.LoginActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun initView(savedInstanceState: Bundle?) {
        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.result.observe(this) {
            when (it) {
                is ViewState.Loading -> {

                }
                is ViewState.Success -> {
                    when (it.data) {
                        is SplashEvent.LaunchLogin -> {
                            openActivity(LoginActivity::class.java)
                        }
                        is SplashEvent.LaunchHome -> {
                            openActivity(HomeActivity::class.java)
                        }
                    }
                    finish()
                }
                is ViewState.Error -> {
                    handlerError(it.error)
                }
            }
        }
    }

}