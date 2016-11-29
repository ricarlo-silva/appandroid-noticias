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
import com.unip.aps.aps_noticias.model.CurtidaModel;
import com.unip.aps.aps_noticias.model.NoticiaModel;
import com.unip.aps.aps_noticias.util.ApsNoticiasUtils;

import java.text.ParseException;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ricarlo on 12/11/2016.
 */

public class RecycleAdapterMyNews extends RecyclerView.Adapter<RecycleAdapterMyNews.ViewHolder>{

    private List<NoticiaModel> postsList;
    private Context context;
    private Callback mCallback;

    public interface Callback {
        void onItemClicked(int index);
        void onItemUpdate(int index);
        void onItemDelete(int index);
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
        public ImageView iv_like_yes;
        public ImageView iv_like_no;
        public ImageView iv_delete_my_news;
        public ImageView iv_update_my_news;
        final RecycleAdapterMyNews adapter;

        public ViewHolder(View v, RecycleAdapterMyNews adapter) {
            super(v);
            iv_foto_user = (ImageView) v.findViewById(R.id.iv_foto_user);
            tv_nome_user = (TextView) v.findViewById(R.id.tv_nome_user);
            tv_data = (TextView) v.findViewById(R.id.tv_data);
            tv_titulo = (TextView) v.findViewById(R.id.tv_title);
            tv_conteudo = (TextView) v.findViewById(R.id.tv_conteudo);
            tv_ver_mais = (TextView) v.findViewById(R.id.tv_ver_mais);

            tv_like_yes = (TextView) v.findViewById(R.id.tv_like_yes);
            tv_like_yes.setVisibility(View.GONE);

            tv_like_no = (TextView) v.findViewById(R.id.tv_like_no);
            tv_like_no.setVisibility(View.GONE);

            iv_like_yes = (ImageView) v.findViewById(R.id.iv_like_yes);
            iv_like_yes.setVisibility(View.GONE);

            iv_like_no = (ImageView) v.findViewById(R.id.iv_like_no);
            iv_like_no.setVisibility(View.GONE);

            iv_update_my_news = (ImageView) v.findViewById(R.id.iv_update_my_news);
            iv_update_my_news.setVisibility(View.VISIBLE);

            iv_delete_my_news = (ImageView) v.findViewById(R.id.iv_delete_my_news);
            iv_delete_my_news.setVisibility(View.VISIBLE);

            this.adapter = adapter;
            tv_ver_mais.setOnClickListener(this);
            tv_titulo.setOnClickListener(this);
            tv_conteudo.setOnClickListener(this);
            iv_update_my_news.setOnClickListener(this);
            iv_delete_my_news.setOnClickListener(this);
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
                case R.id.iv_update_my_news:
                    adapter.mCallback.onItemUpdate(getAdapterPosition());
                    break;
                case R.id.iv_delete_my_news:
                    adapter.mCallback.onItemDelete(getAdapterPosition());
                    break;
            }
        }
    }

    public RecycleAdapterMyNews(Context context, List<NoticiaModel> noticiasList){
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
            //holder.tv_data.setText(ApsNoticiasUtils.converteDatePost(noticia.getData_publicacao()));
            //holder.tv_data.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
            holder.tv_data.setVisibility(View.INVISIBLE);
        }

        holder.tv_titulo.setText(noticia.getTitulo());
        holder.tv_conteudo.setText(noticia.getDescricao());

    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

}
