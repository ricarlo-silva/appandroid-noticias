package com.noticiasnow.account.register

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import br.com.ricarlo.common.inapp.review.ReviewFlow
import br.com.ricarlo.common.inapp.review.ReviewViewModel
import br.com.ricarlo.common.ui.base.BaseActivity
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.extensions.getString
import br.com.ricarlo.common.util.extensions.openPlayStore
import br.com.ricarlo.common.util.extensions.showToast
import com.google.android.play.core.review.ReviewManager
import com.noticiasnow.R
import com.noticiasnow.databinding.ActivityRegisterBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_register
    }

    private val reviewManager: ReviewManager by inject()
    private val reviewViewModel: ReviewViewModel by inject()
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun initView(savedInstanceState: Bundle?) {

        binding.btRegister.setOnClickListener {
            registerViewModel.insert(
                name = binding.edName.getString(),
                email = binding.edEmail.getString(),
                password = binding.edPassword.getString(),
                photo = ""
            )
        }

        subscribeUI()
        reviewViewModel.requestReviewFlow()
    }

    private fun subscribeUI() {

        reviewViewModel.reviewFlow.observe(
            this,
            Observer { reviewFlow ->
                when (reviewFlow) {
                    is ReviewFlow.LaunchInApp -> {
                        reviewManager.launchReviewFlow(this, reviewFlow.reviewInfo)
                            .addOnCompleteListener {
                                Log.e("launchReviewFlow", "$it")
                                if (!it.isSuccessful) {
                                    showToast(it.exception?.message.orEmpty())
                                }
                            }
                    }
                    is ReviewFlow.LaunchOutApp -> {
                        openPlayStore()
                    }
                    is ReviewFlow.Error -> {
                        showToast(reviewFlow.exception?.message.orEmpty())
                    }
                }
            }
        )

        registerViewModel.user.observe(
            this,
            Observer {
                when (it) {
                    is ViewState.Loading -> {
                        showLoading(getString(R.string.criando_usuario))
                    }
                    is ViewState.Success -> {
                        hideLoading()
                        setResult(RESULT_OK)
                        finish()
                    }
                    is ViewState.Error -> {
                        hideLoading()
                        handlerError(it.error)
                    }
                }
            }
        )
    }

    companion object {
        const val REQUEST_REGISTER = 124
    }
}
