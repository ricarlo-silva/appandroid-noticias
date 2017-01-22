package com.noticias_now.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.noticias_now.R;
import com.noticias_now.app.ApsNoticiasApp;
import com.noticias_now.model.CurtidaModel;
import com.noticias_now.model.NoticiaModel;
import com.noticias_now.util.ApsNoticiasUtils;

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

    public interface OnGetNoticias {
        void onSuccess(List<NoticiaModel> list);
        void onError(String error);
    }
    public static void getNoticiasByType(String type, final OnGetNoticias callback){

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


    public interface OnGetALLNoticiasPerson {
        void onSuccess(List<NoticiaModel> list);
        void onError(String error);
    }
    public static void getALLNoticiasPerson(String id_person, final OnGetALLNoticiasPerson callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.getAllNoticiasPerson(id_person);
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

    public interface OnInserirNews {
        void onSuccess(NoticiaModel noticia);
        void onError(String error);
    }
    public static void inserirNews(NoticiaModel noticia, final OnInserirNews callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.inserirNoticia(noticia);
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

    public interface OnAlterarNews {
        void onSuccess(NoticiaModel noticia);
        void onError(String error);
    }
    public static void alterarNews(NoticiaModel noticia, final OnAlterarNews callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.alterarNoticia(noticia);
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



    public interface OnDeletarNews {
        void onSuccess(String msg);
        void onError(String error);
    }
    public static void deletarNews(NoticiaModel noticia, final OnDeletarNews callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.deletarNoticia(noticia);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null && callback != null) {
                    if (response.body().has("error") && response.body().get("error").getAsBoolean() == false ){
                        callback.onSuccess(response.body().get("message").toString());

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
