package com.noticias_now.account.update

import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.extensions.getString
import br.com.ricarlo.common.util.extensions.showToast
import com.noticias_now.R
import com.noticias_now.databinding.ActivityEditProfileBinding
import br.com.ricarlo.common.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>() {

    private val viewModel: EditProfileViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.activity_edit_profile
    }

    override fun initView(savedInstanceState: Bundle?) {
        setUpToolBar(getString(R.string.edit_profile_title))

        binding.btActivityEditarPerfilSalvar.setOnClickListener {
            viewModel.update(
                    name = binding.edNomeEditarActivity.getString(),
                    email = binding.edEmailEditarActivity.getString(),
                    password = binding.edSenhaEditarActivity.getString()
            )
        }
        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.result.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                    showLoading(getString(R.string.salvando_usuario))
                }
                is ViewState.Success -> {
                    hideLoading()
                    showToast(getString(R.string.usuario_atualizado))
                    finish()
                }
                is ViewState.Error -> {
                    hideLoading()
                    handlerError(it.error)
                }
            }
        })

        viewModel.user.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                }
                is ViewState.Success -> {
                    binding.edNomeEditarActivity.setText(it.data.name)
                    binding.edEmailEditarActivity.setText(it.data.email)
                }
                is ViewState.Error -> {
                    hideLoading()
                    handlerError(it.error)
                }
            }
        })
    }

}