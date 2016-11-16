package com.unip.aps.aps_noticias.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.unip.aps.aps_noticias.R;
import com.unip.aps.aps_noticias.model.NoticiaModel;

public class DetalheNoticiaActivity extends BaseActivity {

    public static final String BUNDLE_NOTICIA = "bundle_noticia";

    private NoticiaModel noticia;
    private TextView tv_texto;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tv_texto = (TextView) findViewById(R.id.tv_text_detalhe_noticia);

    }

    private void setData(){
        tv_texto.setText(noticia.getTextoPublicacao());
    }

}
