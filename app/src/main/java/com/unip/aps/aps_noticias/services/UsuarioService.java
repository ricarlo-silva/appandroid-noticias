package com.unip.aps.aps_noticias.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.unip.aps.aps_noticias.R;
import com.unip.aps.aps_noticias.app.ApsNoticiasApp;
import com.unip.aps.aps_noticias.model.UsuarioModel;
import com.unip.aps.aps_noticias.util.ApsNoticiasUtils;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ricarlo on 13/11/2016.
 */

public class UsuarioService {

    private static IApsNoticias iApsNoticias;



    public interface OnCriarUsuario{
        void onSuccess();
        void onError(String error);
    }
    public static void criarUsuario(UsuarioModel usuario, final OnCriarUsuario callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.inserirUsuario(ApsNoticiasUtils.objectToString(usuario));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response != null && callback != null) {
                    if (response.code() == 200)
                        callback.onSuccess();
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




    public interface OnLoginUsuario{
        void onSuccess(UsuarioModel usuario);
        void onError(String error);
    }
    public static void loginUsuario(UsuarioModel usuario, final OnLoginUsuario callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.loginUsuario(ApsNoticiasUtils.objectToString(usuario));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response != null && callback != null) {
                    if (response.code() == 200){
                        Type type = new TypeToken<UsuarioModel>() {}.getType();
                        callback.onSuccess((UsuarioModel) new Gson().fromJson(response.body().toString(), type));
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




    public interface OnGetUsuario{
        void onSuccess(UsuarioModel usuario);
        void onError(String error);
    }
    public static void getUsuario(long id, final OnGetUsuario callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.getUsuario(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response != null && callback != null) {
                    if (response.code() == 200){
                        Type type = new TypeToken<UsuarioModel>() {}.getType();
                        callback.onSuccess((UsuarioModel) new Gson().fromJson(response.body().toString(), type));
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




    public interface OnAlterarUsuario{
        void onSuccess();
        void onError(String error);
    }
    public static void alterarUsuario(UsuarioModel usuario, final OnAlterarUsuario callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.alterarUsuario(ApsNoticiasUtils.objectToString(usuario));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response != null && callback != null) {
                    if (response.code() == 200)
                        callback.onSuccess();
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




    public interface OnExcluirUsuario{
        void onSuccess();
        void onError(String error);
    }
    public static void excluirUsuario(UsuarioModel usuario, final OnExcluirUsuario callback){


        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.excluirUsuario(ApsNoticiasUtils.objectToString(usuario));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response != null && callback != null) {
                    if (response.code() == 200)
                        callback.onSuccess();
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
