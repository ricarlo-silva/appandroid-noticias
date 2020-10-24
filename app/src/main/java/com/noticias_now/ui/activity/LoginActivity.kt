package com.noticias_now.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.noticias_now.R
import com.noticias_now.app.ApsNoticiasApp
import com.noticias_now.model.UsuarioModel
import com.noticias_now.services.UsuarioService
import com.noticias_now.services.UsuarioService.OnLoginUsuario
import com.noticias_now.util.showToast

class LoginActivity : BaseActivity(), View.OnClickListener {

    private var ed_email: EditText? = null
    private var ed_password: EditText? = null
    private var bt_logar: Button? = null
    private var tv_criar_conta: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (ApsNoticiasApp.instance?.checkIfLogged()==true) {
            openActivity(this, HomeActivity::class.java)
            finish()
        }
        setUpToolBar(getString(R.string.titulo_login))
        ed_email = findViewById(R.id.ed_email_login_activity)
        ed_password = findViewById(R.id.ed_password_login_activity)
        bt_logar = findViewById(R.id.bt_activity_login_logar)
        bt_logar?.setOnClickListener(this)
        tv_criar_conta = findViewById(R.id.tv_activity_login_criar_conta)
        tv_criar_conta?.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_activity_login_logar -> clickLogar()
            R.id.tv_activity_login_criar_conta -> {
                openActivity(
                        this@LoginActivity,
                        CriarContaActivity::class.java,
                        CriarContaActivity.REQUEST_CRIAR_CONTA)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CriarContaActivity.REQUEST_CRIAR_CONTA && resultCode == RESULT_OK) {
            showToast(getString(R.string.boas_vindas))
            openActivity(this, HomeActivity::class.java)
            finish()
        }
    }

    private fun clickLogar() {
        val email = ed_email?.text.toString()
        val senha = ed_password?.text.toString()
        if (email.isNotEmpty() && senha.isNotEmpty()) {
            val user = UsuarioModel()
            user.email = email
            user.senha = senha
            login(user)
        } else {
            showToast(getString(R.string.preencher_campos))
        }
    }

    private fun login(user: UsuarioModel) {
        showProgressDialog(true, getString(R.string.realizando_login))
        UsuarioService.loginUsuario(user, object : OnLoginUsuario {
            override fun onSuccess(usuario: UsuarioModel) {
                showProgressDialog(false, null)
                showToast(getString(R.string.boas_vindas))
                ApsNoticiasApp.instance?.saveUser(usuario)
                openActivity(this@LoginActivity, HomeActivity::class.java)
                finish()
            }

            override fun onError(error: String) {
                showProgressDialog(false, null)
                showToast(error)
            }
        })
    }

}