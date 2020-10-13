package com.noticias_now.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.noticias_now.R;
import com.noticias_now.model.NoticiaModel;
import com.noticias_now.services.NoticiaService;
import com.noticias_now.ui.adapter.RecycleAdapterMyNews;

import java.util.ArrayList;
import java.util.List;

public class ListaNoticiasUsuarioActivity extends BaseActivity {


    private RecyclerView recyclerView;
    private RecycleAdapterMyNews adapter;
    private List<NoticiaModel> noticiaList;
    private FrameLayout fl_add_news;
    private ImageView iv_add_news;
    private FrameLayout fl_back;
    private ImageView iv_back;

    private int index_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_noticias_usuario);

        setUpToolBar("Minhas Publicações");
        initView();
    }

    private void initView(){


        fl_back = (FrameLayout) toolbar.findViewById(R.id.fl_back);
        fl_back.setVisibility(View.VISIBLE);
        iv_back = (ImageView) toolbar.findViewById(R.id.ivBack);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        fl_add_news = (FrameLayout) toolbar.findViewById(R.id.fl_settings);
        fl_add_news.setVisibility(View.VISIBLE);
        iv_add_news = (ImageView) toolbar.findViewById(R.id.ivSettings);
        iv_add_news.setBackgroundResource(R.drawable.ic_add_news);
        iv_add_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityForResult(PublicarNoticiaActivity.class, PublicarNoticiaActivity.REQUEST_PUBLICACAO);
            }
        });

        SwipeRefreshLayout swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipe_refresh.setEnabled(false);

        noticiaList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.rv_my_news);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecycleAdapterMyNews(this, noticiaList);
        adapter.setCallback(new RecycleAdapterMyNews.Callback() {
            @Override
            public void onItemClicked(int index) {
                NoticiaModel noticia = noticiaList.get(index);
                openActivity(ListaNoticiasUsuarioActivity.this, DetalheNoticiaActivity.class, DetalheNoticiaActivity.BUNDLE_NOTICIA, noticia);
            }

            @Override
            public void onItemUpdate(int index) {
                NoticiaModel noticia = noticiaList.get(index);
                openActivityForResult(PublicarNoticiaActivity.class, PublicarNoticiaActivity.BUNDLE_NOTICIA, noticia,PublicarNoticiaActivity.REQUEST_PUBLICACAO);
            }

            @Override
            public void onItemDelete(int index) {
                index_delete = index;
                NoticiaModel noticia = noticiaList.get(index);
                dialogDeletarNews(noticia);
            }
        });

        recyclerView.setAdapter(adapter);

        getAllNewsPerson();
    }

    private void getAllNewsPerson(){

        showProgressDialog(true, getString(R.string.carregando));
        NoticiaService.getALLNoticiasPerson(usuario.getId(), new NoticiaService.OnGetALLNoticiasPerson() {
            @Override
            public void onSuccess(List<NoticiaModel> list) {
                showProgressDialog(false, null);
                if(list != null && list.size() > 0){
                    noticiaList.clear();
                    noticiaList.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String error) {
                showProgressDialog(false, null);
                Toast.makeText(ListaNoticiasUsuarioActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dialogDeletarNews(final NoticiaModel noticia){
        new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(R.string.deletar_news)
                .positiveText(R.string.sim)
                .negativeText(R.string.nao)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deletarNews(noticia);
                    }
                })
                .show();
    }

    private void deletarNews(NoticiaModel noticia){

        showProgressDialog(true, getString(R.string.carregando));
        NoticiaService.deletarNews(noticia, new NoticiaService.OnDeletarNews() {
            @Override
            public void onSuccess(String msg) {
                showProgressDialog(false, null);
                Toast.makeText(ListaNoticiasUsuarioActivity.this, msg, Toast.LENGTH_SHORT).show();

                noticiaList.remove(index_delete);
                adapter.notifyItemRemoved(index_delete);
            }

            @Override
            public void onError(String error) {
                showProgressDialog(false, null);
                Toast.makeText(ListaNoticiasUsuarioActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PublicarNoticiaActivity.REQUEST_PUBLICACAO&& resultCode == RESULT_OK){
            getAllNewsPerson();
        }
    }
}
