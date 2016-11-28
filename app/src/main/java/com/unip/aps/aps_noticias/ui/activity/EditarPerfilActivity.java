package com.unip.aps.aps_noticias.ui.activity;

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

public class EditarPerfilActivity extends BaseActivity {

    private EditText ed_nome;
    private EditText ed_email;
    private EditText ed_senha;

    private Button bt_salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        setUpToolBar("Editar Perfil");

        ed_nome = (EditText) findViewById(R.id.ed_nome_editar_activity);
        ed_nome.setText(usuario.getName());
        ed_email = (EditText) findViewById(R.id.ed_email_editar_activity);
        ed_email.setText(usuario.getEmail());
        ed_senha = (EditText) findViewById(R.id.ed_senha_editar_activity);

        bt_salvar = (Button) findViewById(R.id.bt_activity_editar_perfil_salvar);
        bt_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSalvar();
            }
        });

    }

    private void clickSalvar(){
        String nome = ed_nome.getText().toString();
        String email = ed_email.getText().toString();
        String senha = ed_senha.getText().toString();

        if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)) {

            UsuarioModel user = new UsuarioModel();
            user.setId(usuario.getId());
            user.setName(nome);
            user.setEmail(email);
            user.setSenha(senha);

            salvar(user);

        }else{
            Toast.makeText(this, R.string.preencher_campos, Toast.LENGTH_SHORT).show();
        }
    }

    private void salvar(UsuarioModel user){

        showProgressDialog(true, getString(R.string.salvando_usuario));
        UsuarioService.alterarUsuario(user, new UsuarioService.OnAlterarUsuario() {
            @Override
            public void onSuccess(UsuarioModel usuario) {
                showProgressDialog(false, null);

                Toast.makeText(EditarPerfilActivity.this, R.string.usuario_atualizado, Toast.LENGTH_SHORT).show();
                ApsNoticiasApp.getInstance().saveUser(usuario);
                finish();
            }

            @Override
            public void onError(String error) {
                showProgressDialog(false, null);
                Toast.makeText(EditarPerfilActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
