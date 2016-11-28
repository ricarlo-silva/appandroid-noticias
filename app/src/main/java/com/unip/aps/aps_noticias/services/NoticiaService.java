package com.unip.aps.aps_noticias.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.unip.aps.aps_noticias.R;
import com.unip.aps.aps_noticias.app.ApsNoticiasApp;
import com.unip.aps.aps_noticias.model.CurtidaModel;
import com.unip.aps.aps_noticias.model.NoticiaModel;
import com.unip.aps.aps_noticias.model.UsuarioModel;
import com.unip.aps.aps_noticias.util.ApsNoticiasUtils;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ricarlo on 13/11/2016.
 */

public class NoticiaService {

    private static IApsNoticias iApsNoticias;

    public interface OnGetNotices {
        void onSuccess(List<NoticiaModel> list);
        void onError(String error);
    }
    public static void getNoticiasByType(String type, final OnGetNotices callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.getNoticiaByType(type);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null && callback != null) {
                    if (response.body().has("error") && response.body().get("error").getAsBoolean() == false ){

                        Type type = new TypeToken<List<NoticiaModel>>() {}.getType();
                        callback.onSuccess((List<NoticiaModel>) new Gson().fromJson(response.body().get("data").toString(), type));

                    } else if (response.body().has("message")){
                        callback.onError(response.body().get("message").toString());
                    } else{
                        callback.onError(ApsNoticiasApp.getInstance().getString(R.string.error_generico));
                    }
                }else{
                    if(callback != null)
                        callback.onError(ApsNoticiasApp.getInstance().getString(R.string.error_generico));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if(callback != null)
                    callback.onError(ApsNoticiasApp.getInstance().getString(R.string.error_generico));
            }
        });
    }



    public interface OnGetNewsById {
        void onSuccess(NoticiaModel noticia);
        void onError(String error);
    }
    public static void getNewsById(String id_news, final OnGetNewsById callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.getNoticiaById(id_news);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null && callback != null) {
                    if (response.body().has("error") && response.body().get("error").getAsBoolean() == false ){

                        Type type = new TypeToken<NoticiaModel>() {}.getType();
                        callback.onSuccess((NoticiaModel) new Gson().fromJson(response.body().get("data").toString(), type));

                    } else if (response.body().has("message")){
                        callback.onError(response.body().get("message").toString());
                    } else{
                        callback.onError(ApsNoticiasApp.getInstance().getString(R.string.error_generico));
                    }
                }else{
                    if(callback != null)
                        callback.onError(ApsNoticiasApp.getInstance().getString(R.string.error_generico));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if(callback != null)
                    callback.onError(ApsNoticiasApp.getInstance().getString(R.string.error_generico));
            }
        });
    }



    public interface OnCreateLike {
        void onSuccess(NoticiaModel noticia);
        void onError(String error);
    }
    public static void createLike(CurtidaModel curtida, final OnCreateLike callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.createLike(curtida);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null && callback != null) {
                    if (response.body().has("error") && response.body().get("error").getAsBoolean() == false ){

                        Type type = new TypeToken<NoticiaModel>() {}.getType();
                        callback.onSuccess((NoticiaModel) new Gson().fromJson(response.body().get("data").toString(), type));

                    } else if (response.body().has("message")){
                        callback.onError(response.body().get("message").toString());
                    } else{
                        callback.onError(ApsNoticiasApp.getInstance().getString(R.string.error_generico));
                    }
                }else{
                    if(callback != null)
                        callback.onError(ApsNoticiasApp.getInstance().getString(R.string.error_generico));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if(callback != null)
                    callback.onError(ApsNoticiasApp.getInstance().getString(R.string.error_generico));
            }
        });
    }

}
