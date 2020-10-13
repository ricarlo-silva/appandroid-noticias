package com.noticias_now.ui.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.noticias_now.R;
import com.noticias_now.model.NoticiaModel;
import com.noticias_now.services.NoticiaService;
import com.noticias_now.ui.activity.DetalheNoticiaActivity;
import com.noticias_now.ui.adapter.RecycleAdapter;

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

    @Override
    public void onResume() {
        super.onResume();
        getNoticiasTransito();
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


    }

    private void getNoticiasTransito(){

        String type = "2";

        showProgressDialog(true, getString(R.string.carregando));
        NoticiaService.getNoticiasByType(type, new NoticiaService.OnGetNoticias() {
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
