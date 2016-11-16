package com.unip.aps.aps_noticias.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unip.aps.aps_noticias.R;
import com.unip.aps.aps_noticias.app.ApsNoticiasApp;
import com.unip.aps.aps_noticias.model.UsuarioModel;
import com.unip.aps.aps_noticias.services.UsuarioService;

public class CriarContaActivity extends BaseActivity {

    public static final int REQUEST_CRIAR_CONTA = 124;

    private EditText ed_nome;
    private EditText ed_email;
    private EditText ed_password;
    private Button bt_criar_conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        ed_nome = (EditText) findViewById(R.id.ed_nome_criar_activity);
        ed_email = (EditText) findViewById(R.id.ed_email_criar_activity);
        ed_password = (EditText) findViewById(R.id.ed_senha_criar_activity);

        bt_criar_conta = (Button) findViewById(R.id.bt_conta_criar_activity);
        bt_criar_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCriarConta();
            }
        });
    }

    private void clickCriarConta(){
        String nome = ed_nome.getText().toString();
        String email = ed_email.getText().toString();
        String senha = ed_password.getText().toString();

        if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)) {

            UsuarioModel user = new UsuarioModel();
            user.setNomePerfil(nome);
            user.setEmail(email);
            user.setSenha(senha);

            criarConta(user);

        }else{
            Toast.makeText(this, R.string.preencher_campos, Toast.LENGTH_SHORT).show();
        }
    }

    private void criarConta(UsuarioModel user){
        ApsNoticiasApp.getInstance().saveUser(user);
        setResult(RESULT_OK);
        finish();

//        showProgressDialog(true, getString(R.string.criando_usuario));
//        UsuarioService.criarUsuario(usuario, new UsuarioService.OnCriarUsuario() {
//            @Override
//            public void onSuccess() {
//                showProgressDialog(false, null);
//
//                ApsNoticiasApp.getInstance().saveUser(user);
//                setResult(RESULT_OK);
//                finish();
//            }
//
//            @Override
//            public void onError(String error) {
//                showProgressDialog(false, null);
//                Toast.makeText(CriarContaActivity.this, error, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
