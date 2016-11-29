package com.unip.aps.aps_noticias.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.unip.aps.aps_noticias.R;
import com.unip.aps.aps_noticias.model.CurtidaModel;
import com.unip.aps.aps_noticias.model.NoticiaModel;
import com.unip.aps.aps_noticias.services.NoticiaService;

public class DetalheNoticiaActivity extends BaseActivity {

    public static final String BUNDLE_NOTICIA = "bundle_noticia";

    private NoticiaModel noticia;
    private TextView tv_texto;
    private ImageView iv_like_yes;
    private ImageView iv_like_no;
    private TextView tv_like_yes;
    private TextView tv_like_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_noticia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getBundle();
        getNoticia();
        initView();
        setData();

    }

    private void getBundle(){
        if(getIntent().hasExtra(BUNDLE_NOTICIA)){
            noticia = (NoticiaModel) getIntent().getSerializableExtra(BUNDLE_NOTICIA);
        }else
            finish();
    }

    private void initView(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareNoticia(noticia.getTitulo(), noticia.getDescricao());
            }
        });

        tv_texto = (TextView) findViewById(R.id.tv_text_detalhe_noticia);
        iv_like_yes = (ImageView) findViewById(R.id.iv_like_yes);
        iv_like_no = (ImageView)findViewById(R.id.iv_like_no);
        tv_like_yes = (TextView) findViewById(R.id.tv_like_yes);
        tv_like_no = (TextView)findViewById(R.id.tv_like_no);

        iv_like_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLike(true);
            }
        });

        iv_like_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLike(false);
            }
        });

    }

    private void setData(){
        setTitle(noticia.getTitulo());
        tv_texto.setText(noticia.getDescricao());
        setCurtidas();
    }

    private void setCurtidas(){
        int like_yes = 0;
        int like_no = 0;
        for(CurtidaModel curtida: noticia.getCurtidas()){
            if(curtida.getLike())
                like_yes++;
            else
                like_no++;

            // se usuário logado já tiver curtido
            if(curtida.getId_person() != null && curtida.getId_person().equals(usuario.getId()))
                changeLike(curtida.getLike());
        }
        tv_like_yes.setText(String.valueOf(like_yes));
        tv_like_no.setText(String.valueOf(like_no));
    }

    private void changeLike(boolean like){
        if(like) {
            iv_like_yes.setSelected(true);
            iv_like_no.setSelected(false);
        }else{
            iv_like_yes.setSelected(false);
            iv_like_no.setSelected(true);
        }
    }

    private void createLike(boolean like){
        CurtidaModel curtida = new CurtidaModel();
        curtida.setId_person(usuario.getId());
        curtida.setId_news(noticia.getId());
        curtida.setLike(like);

        System.out.println(new Gson().toJson(usuario));

        NoticiaService.createLike(curtida, new NoticiaService.OnCreateLike() {
            @Override
            public void onSuccess(NoticiaModel _noticia) {
                if(_noticia != null) {
                    noticia = _noticia;
                    setCurtidas();
                }

            }

            @Override
            public void onError(String error) {
                Toast.makeText(DetalheNoticiaActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNoticia(){

        NoticiaService.getNewsById(noticia.getId(), new NoticiaService.OnGetNewsById() {
            @Override
            public void onSuccess(NoticiaModel _noticia) {
                if(_noticia != null) {
                    noticia = _noticia;
                    setCurtidas();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(DetalheNoticiaActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
