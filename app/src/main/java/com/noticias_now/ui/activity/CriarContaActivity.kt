package com.noticias_now.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import br.com.ricarlo.common.inapp.review.ReviewFlow
import br.com.ricarlo.common.inapp.review.ReviewViewModel
import br.com.ricarlo.common.util.extensions.openPlayStore
import com.google.android.play.core.review.ReviewManager
import com.noticias_now.R
import com.noticias_now.app.ApsNoticiasApp
import com.noticias_now.model.UsuarioModel
import com.noticias_now.services.UsuarioService
import com.noticias_now.services.UsuarioService.OnCriarUsuario
import com.noticias_now.util.showToast
import org.koin.android.ext.android.inject

class CriarContaActivity : BaseActivity() {

    private var ed_nome: EditText? = null
    private var ed_email: EditText? = null
    private var ed_password: EditText? = null
    private var bt_criar_conta: Button? = null

    private val reviewManager: ReviewManager by inject()
    private val reviewViewModel: ReviewViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)

        ed_nome = findViewById(R.id.ed_nome_criar_activity)
        ed_email = findViewById(R.id.ed_email_criar_activity)
        ed_password = findViewById(R.id.ed_senha_criar_activity)
        bt_criar_conta = findViewById(R.id.bt_conta_criar_activity)
        bt_criar_conta?.setOnClickListener { clickCriarConta() }


        reviewViewModel.reviewFlow.observe(this, { reviewFlow ->
            when (reviewFlow) {
                is ReviewFlow.LaunchInApp -> {
                    reviewManager.launchReviewFlow(this, reviewFlow.reviewInfo)
                            .addOnCompleteListener {
                                Log.e("launchReviewFlow", "$it")
                                if (!it.isSuccessful) {
                                    showToast(it.exception.message.orEmpty())
                                }
                            }
                }
                is ReviewFlow.LaunchOutApp -> {
                    openPlayStore()
                }
                is ReviewFlow.Error -> {
                    showToast(reviewFlow.exception.message.orEmpty())
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        reviewViewModel.requestReviewFlow()
    }

    private fun clickCriarConta() {
        val nome = ed_nome?.text.toString()
        val email = ed_email?.text.toString()
        val senha = ed_password?.text.toString()
        if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
            val user = UsuarioModel()
            user.name = nome
            user.email = email
            user.senha = senha
            user.photo = ""
            criarConta(user)
        } else {
            showToast(getString(R.string.preencher_campos))
        }
    }

    private fun criarConta(user: UsuarioModel) {
        showProgressDialog(true, getString(R.string.criando_usuario))
        UsuarioService.criarUsuario(user, object : OnCriarUsuario {
            override fun onSuccess(usuario: UsuarioModel) {
                showProgressDialog(false, null)
                ApsNoticiasApp.instance?.saveUser(usuario)
                setResult(RESULT_OK)
                finish()
            }

            override fun onError(error: String) {
                showProgressDialog(false, null)
                showToast(error)
            }
        })
    }

    companion object {
        const val REQUEST_CRIAR_CONTA = 124
    }

}