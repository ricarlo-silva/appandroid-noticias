package com.unip.aps.aps_noticias.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unip.aps.aps_noticias.R;
import com.unip.aps.aps_noticias.model.NoticiaModel;
import com.unip.aps.aps_noticias.services.NoticiaService;
import com.unip.aps.aps_noticias.ui.activity.DetalheNoticiaActivity;
import com.unip.aps.aps_noticias.ui.adapter.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransitoFragment extends BaseFragment {


    private RecyclerView recyclerView;
    private RecycleAdapter adapter;
    private List<NoticiaModel> noticiaList;

    public TransitoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transito, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        SwipeRefreshLayout swipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipe_refresh.setEnabled(false);

        noticiaList = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_transito);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecycleAdapter(getActivity(), noticiaList);
        adapter.setCallback(new RecycleAdapter.Callback() {
            @Override
            public void onItemClicked(int index) {
                NoticiaModel noticia = noticiaList.get(index);

                openActivity(DetalheNoticiaActivity.class, DetalheNoticiaActivity.BUNDLE_NOTICIA, noticia);
            }
        });

        recyclerView.setAdapter(adapter);

        getNoticiasPoluicao();
    }

    private void getNoticiasPoluicao(){

        String type = "2";

        showProgressDialog(true, getString(R.string.carregando));
        NoticiaService.getNoticiasByType(type, new NoticiaService.OnGetNotices() {
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
                Toast.makeText(getBaseActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
