package com.unip.aps.aps_noticias.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unip.aps.aps_noticias.R;
import com.unip.aps.aps_noticias.model.NoticiaModel;
import com.unip.aps.aps_noticias.util.ApsNoticiasUtils;

import java.text.ParseException;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ricarlo on 12/11/2016.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>{

    private List<NoticiaModel> postsList;
    private Context context;
    private Callback mCallback;

    public interface Callback {
        void onItemClicked(int index);
    }

    public void setCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView iv_foto_user;
        public TextView tv_nome_user;
        public TextView tv_data;
        public TextView tv_titulo;
        public TextView tv_conteudo;
        public TextView tv_ver_mais;
        public TextView tv_votos;
        final RecycleAdapter adapter;

        public ViewHolder(View v, RecycleAdapter adapter) {
            super(v);
            iv_foto_user = (ImageView) v.findViewById(R.id.iv_foto_user);
            tv_nome_user = (TextView) v.findViewById(R.id.tv_nome_user);
            tv_data = (TextView) v.findViewById(R.id.tv_data);
            tv_titulo = (TextView) v.findViewById(R.id.tv_title);
            tv_conteudo = (TextView) v.findViewById(R.id.tv_conteudo);
            tv_ver_mais = (TextView) v.findViewById(R.id.tv_ver_mais);
            tv_votos = (TextView) v.findViewById(R.id.tv_votos);

            this.adapter = adapter;
            tv_ver_mais.setOnClickListener(this);
            tv_titulo.setOnClickListener(this);
            tv_conteudo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(adapter == null)
                return;

            int id = v.getId();
            switch (id){
                case R.id.tv_ver_mais:
                    adapter.mCallback.onItemClicked(getAdapterPosition());

                    break;
                case R.id.tv_title:
                    adapter.mCallback.onItemClicked(getAdapterPosition());

                    break;
                case R.id.tv_conteudo:
                    adapter.mCallback.onItemClicked(getAdapterPosition());

                    break;
            }
        }
    }

    public RecycleAdapter(Context context, List<NoticiaModel> noticiasList){
        this.postsList = noticiasList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoticiaModel noticia = postsList.get(position);

        if(!TextUtils.isEmpty(noticia.getFotoPerfil())){
            Glide.with(context).load(noticia.getFotoPerfil())
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.user))
                    .crossFade()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.iv_foto_user);
        }else{
            holder.iv_foto_user.setImageResource(R.drawable.user);
        }

        holder.tv_nome_user.setText(noticia.getNomePerfil());

        try {
            holder.tv_data.setText(ApsNoticiasUtils.converteDatePost(noticia.getDataHora()));
            holder.tv_data.setVisibility(View.VISIBLE);

        } catch (ParseException e) {
            e.printStackTrace();
            holder.tv_data.setVisibility(View.INVISIBLE);
        }

        holder.tv_titulo.setText(noticia.getTituloPublicacao());
        holder.tv_conteudo.setText(noticia.getTextoPublicacao());
        holder.tv_votos.setText(String.format("%d curtiram", noticia.getQuantidadeVotos()));
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }
}
