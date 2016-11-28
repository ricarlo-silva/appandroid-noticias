package com.unip.aps.aps_noticias.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.unip.aps.aps_noticias.R;
import com.unip.aps.aps_noticias.app.ApsNoticiasApp;
import com.unip.aps.aps_noticias.model.UsuarioModel;
import com.unip.aps.aps_noticias.services.UsuarioService;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText ed_email;
    private EditText ed_password;
    private Button bt_logar;
    private TextView tv_criar_conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ApsNoticiasApp.getInstance().checkIfLoggedin()){
            openActivity(this, HomeActivity.class);
            finish();
        }

        setUpToolBar(getString(R.string.titulo_login));

        ed_email = (EditText) findViewById(R.id.ed_email_login_activity);
        ed_password = (EditText) findViewById(R.id.ed_password_login_activity);

        bt_logar = (Button) findViewById(R.id.bt_activity_login_logar);
        bt_logar.setOnClickListener(this);

        tv_criar_conta = (TextView) findViewById(R.id.tv_activity_login_criar_conta);
        tv_criar_conta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.bt_activity_login_logar:
                clickLogar();
                break;
            case R.id.tv_activity_login_criar_conta:
                openActivity(LoginActivity.this, CriarContaActivity.class, CriarContaActivity.REQUEST_CRIAR_CONTA);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CriarContaActivity.REQUEST_CRIAR_CONTA && resultCode == RESULT_OK){
            Toast.makeText(this, R.string.boas_vindas, Toast.LENGTH_SHORT).show();
            openActivity(this, HomeActivity.class);
            finish();
        }
    }

    private void clickLogar() {
        String email = ed_email.getText().toString();
        String senha = ed_password.getText().toString();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)) {

            UsuarioModel user = new UsuarioModel();
            user.setEmail(email);
            user.setSenha(senha);

            login(user);

        }else{
            Toast.makeText(this, R.string.preencher_campos, Toast.LENGTH_SHORT).show();
        }
    }

    private void login(UsuarioModel user){

        showProgressDialog(true, getString(R.string.realizando_login));
        UsuarioService.loginUsuario(user, new UsuarioService.OnLoginUsuario() {
            @Override
            public void onSuccess(UsuarioModel usuario) {
                showProgressDialog(false, null);
                Toast.makeText(LoginActivity.this, R.string.boas_vindas, Toast.LENGTH_SHORT).show();

                ApsNoticiasApp.getInstance().saveUser(usuario);
                openActivity(LoginActivity.this, HomeActivity.class);
                finish();

            }

            @Override
            public void onError(String error) {
                showProgressDialog(false, null);
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
