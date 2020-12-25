package com.noticias_now.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.ricarlo.common.ui.base.BaseActivity
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.extensions.getString
import br.com.ricarlo.common.util.extensions.showToast
import com.noticias_now.R
import com.noticias_now.account.register.RegisterActivity
import com.noticias_now.databinding.ActivityLoginBinding
import com.noticias_now.home.HomeActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.activity_login
    }

    override fun initView(savedInstanceState: Bundle?) {
        setUpToolBar(getString(R.string.titulo_login))

        binding.btLogin.setOnClickListener {
            viewModel.login(
                    email = binding.edEmail.getString(),
                    password = binding.edPassword.getString()
            )
        }

        binding.btCreateAccount.setOnClickListener {
            openActivity(
                    RegisterActivity::class.java, null,
                    RegisterActivity.REQUEST_REGISTER
            )
        }

        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.user.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                    showLoading(getString(R.string.realizando_login))
                }
                is ViewState.Success -> {
                    hideLoading()
                    goToHome()
                }
                is ViewState.Error -> {
                    hideLoading()
                    handlerError(it.error)
                }
            }
        })
    }

    private fun goToHome() {
        showToast(getString(R.string.welcome))
        openActivity(HomeActivity::class.java)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RegisterActivity.REQUEST_REGISTER && resultCode == RESULT_OK) {
            goToHome()
        }
    }

}