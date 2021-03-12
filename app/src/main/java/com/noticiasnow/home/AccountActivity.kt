package com.noticiasnow.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.ricarlo.common.ui.base.BaseActivity
import br.com.ricarlo.common.util.ViewState
import com.afollestad.materialdialogs.MaterialDialog
import com.noticiasnow.R
import com.noticiasnow.databinding.ActivityAccountBinding
import com.noticiasnow.login.LoginActivity
import org.koin.android.viewmodel.ext.android.viewModel

class AccountActivity : BaseActivity<ActivityAccountBinding>(), AccountAdapter.OnClickListener {

    private val viewModel: AccountViewModel by viewModel()
    private val _adapter by lazy { AccountAdapter(this) }

    override fun getLayoutRes(): Int {
        return R.layout.activity_account
    }

    override fun initView(savedInstanceState: Bundle?) {
        _adapter.submitList(
            listOf(
                R.string.option_settings,
                R.string.option_logout
            )
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AccountActivity)
            adapter = _adapter
        }
        subscribeUI()
    }

    override fun onClickItem(item: Int) {
        when (item) {
            R.string.option_settings -> {
                // TODO
            }
            R.string.option_logout -> {
                logout()
            }
        }
    }

    private fun subscribeUI() {
        viewModel.logout.observe(
            this,
            Observer {
                when (it) {
                    is ViewState.Loading -> {
                    }
                    is ViewState.Success -> {
                        hideLoading()
                        openActivity(LoginActivity::class.java, null, null)
                        finish()
                    }
                    is ViewState.Error -> {
                        hideLoading()
                        handlerError(it.error)
                    }
                }
            }
        )

        viewModel.user.observe(
            this,
            Observer {
                binding.tvName.text = it.name
                binding.tvEmail.text = it.email
            }
        )
    }

    private fun logout() {
        MaterialDialog.Builder(this)
            .title(R.string.app_name)
            .content(R.string.sair)
            .positiveText(R.string.sim)
            .negativeText(R.string.nao)
            .onPositive { _, _ ->
                viewModel.logout()
            }
            .show()
    }
}
