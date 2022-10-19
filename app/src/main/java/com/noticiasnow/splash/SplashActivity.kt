package com.noticiasnow.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.ricarlo.common.ui.base.BaseActivity
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.extensions.doOnTransitionCompleted
import com.noticiasnow.R
import com.noticiasnow.databinding.ActivitySplashBinding
import com.noticiasnow.home.HomeActivity
import com.noticiasnow.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun initView(savedInstanceState: Bundle?) {

        binding.splashMotionLayout.doOnTransitionCompleted { _, _ ->
            viewModel.setCompletedAnimation()
        }
        binding.splashMotionLayout.startLayoutAnimation()

        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.result.observe(
            this,
            Observer {
                when (it) {
                    is ViewState.Loading -> {
                    }
                    is ViewState.Success -> {
                        val cls = when (it.data) {
                            is SplashEvent.LaunchLogin -> {
                                LoginActivity::class.java
                            }
                            is SplashEvent.LaunchHome -> {
                                HomeActivity::class.java
                            }
                        }
                        startActivity(Intent(this, cls))
                        finish()
                    }
                    is ViewState.Error -> {
                        handlerError(it.error)
                    }
                }
            }
        )
    }
}
