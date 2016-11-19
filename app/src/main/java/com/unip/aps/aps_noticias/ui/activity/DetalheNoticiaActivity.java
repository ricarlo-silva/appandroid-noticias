package com.unip.aps.aps_noticias.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_noticia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getBundle();
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
    }

    private void createLike(boolean like){
        CurtidaModel curtida = new CurtidaModel();
        curtida.setIdPerson(usuario.getId());
        curtida.setIdNews(noticia.getId());
        curtida.setLike(like);
        Toast.makeText(this, "Curtiu:" + like, Toast.LENGTH_SHORT).show();

//        NoticiaService.createLike(curtida, new NoticiaService.OnCreateLike() {
//            @Override
//            public void onSuccess(NoticiaModel noticia) {
//
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        });
    }

}
