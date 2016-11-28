package com.unip.aps.aps_noticias.ui.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.unip.aps.aps_noticias.R;
import com.unip.aps.aps_noticias.model.CurtidaModel;
import com.unip.aps.aps_noticias.model.NoticiaModel;
import com.unip.aps.aps_noticias.services.NoticiaService;

import java.util.ArrayList;

public class PublicarNoticiaActivity extends BaseActivity {

    private EditText ed_title;
    private Spinner sp_tipo;
    private EditText ed_descricao;
    private Button bt_publicar;
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_noticia);

        setUpToolBar("Publicação");


        ed_title = (EditText) findViewById(R.id.ed_title_activity_publicar_news);
        ed_descricao = (EditText) findViewById(R.id.ed_descricao_activity_publicar_news);
        sp_tipo = (Spinner) findViewById(R.id.sp_tipo_activity_publicar_news);

        sp_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selected = parent.getSelectedItemPosition();
                if(selected != 0)
                    type = String.valueOf(selected);
                else
                    type = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bt_publicar = (Button) findViewById(R.id.bt_activity_publicar_news);
        bt_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPublicar();
            }
        });


    }


    private void clickPublicar(){
        String title = ed_title.getText().toString();
        String descricao = ed_descricao.getText().toString();
        String tipo = type;

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(descricao) && !TextUtils.isEmpty(tipo)) {
            NoticiaModel news = new NoticiaModel();
            news.setTitulo(title);
            news.setTipo(tipo);
            news.setDescricao(descricao);
            news.setPerson(usuario);
            news.setCurtidas(new ArrayList<CurtidaModel>());

            publicar(news);
        }
    }


    private void publicar(NoticiaModel noticia){

        showProgressDialog(true, getString(R.string.publicando));
        NoticiaService.inserirNews(noticia, new NoticiaService.OnInserirNews() {
            @Override
            public void onSuccess(NoticiaModel noticia) {
                showProgressDialog(false, null);

                if(noticia != null) {
                    Toast.makeText(PublicarNoticiaActivity.this, R.string.sucesso_publicao, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                showProgressDialog(false, null);
                Toast.makeText(PublicarNoticiaActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}