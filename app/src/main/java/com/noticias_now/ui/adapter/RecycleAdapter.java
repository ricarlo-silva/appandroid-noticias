package com.noticias_now.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.noticias_now.R;
import com.noticias_now.model.CurtidaModel;
import com.noticias_now.model.NoticiaModel;
import com.noticias_now.util.ApsNoticiasUtils;

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
        public TextView tv_like_yes;
        public TextView tv_like_no;
        final RecycleAdapter adapter;

        public ViewHolder(View v, RecycleAdapter adapter) {
            super(v);
            iv_foto_user = (ImageView) v.findViewById(R.id.iv_foto_user);
            tv_nome_user = (TextView) v.findViewById(R.id.tv_nome_user);
            tv_data = (TextView) v.findViewById(R.id.tv_data);
            tv_titulo = (TextView) v.findViewById(R.id.tv_title);
            tv_conteudo = (TextView) v.findViewById(R.id.tv_conteudo);
            tv_ver_mais = (TextView) v.findViewById(R.id.tv_ver_mais);
            tv_like_yes = (TextView) v.findViewById(R.id.tv_like_yes);
            tv_like_no = (TextView) v.findViewById(R.id.tv_like_no);

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

        if(!TextUtils.isEmpty(noticia.getPerson().getPhoto())){
            Glide.with(context).load(noticia.getPerson().getPhoto())
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.bg_photo))
                    .crossFade()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.iv_foto_user);
        }else{
            holder.iv_foto_user.setImageResource(R.drawable.bg_photo);
        }

        holder.tv_nome_user.setText(noticia.getPerson().getName());

        try {
            holder.tv_data.setText(ApsNoticiasUtils.converteDatePost(noticia.getData_publicacao()));
            holder.tv_data.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
            holder.tv_data.setVisibility(View.INVISIBLE);
        }

        holder.tv_titulo.setText(noticia.getTitulo());
        holder.tv_conteudo.setText(noticia.getDescricao());

        setCurtidas(holder, noticia);
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    private void setCurtidas(ViewHolder holder, NoticiaModel noticia){
        int like_yes = 0;
        int like_no = 0;
        for(CurtidaModel curtida: noticia.getCurtidas()){
            if(curtida.getLike())
                like_yes++;
            else
                like_no++;
        }
        holder.tv_like_yes.setText(String.valueOf(like_yes));
        holder.tv_like_no.setText(String.valueOf(like_no));
    }
}
