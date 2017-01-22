package com.noticias_now.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.noticias_now.R;
import com.noticias_now.app.ApsNoticiasApp;
import com.noticias_now.model.UsuarioModel;
import com.noticias_now.util.ApsNoticiasUtils;

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
        void onSuccess(UsuarioModel usuario);
        void onError(String error);
    }
    public static void criarUsuario(UsuarioModel usuario, final OnCriarUsuario callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.inserirUsuario(usuario);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null && callback != null) {
                    if (response.body().has("error") && response.body().get("error").getAsBoolean() == false ){

                        Type type = new TypeToken<UsuarioModel>() {}.getType();
                        callback.onSuccess((UsuarioModel) new Gson().fromJson(response.body().get("data").toString(), type));

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
        Call<JsonObject> call = iApsNoticias.loginUsuario(usuario);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null && callback != null) {
                    if (response.body().has("error") && response.body().get("error").getAsBoolean() == false ){

                        Type type = new TypeToken<UsuarioModel>() {}.getType();
                        callback.onSuccess((UsuarioModel) new Gson().fromJson(response.body().get("data").toString(), type));

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




    public interface OnGetUsuario{
        void onSuccess(UsuarioModel usuario);
        void onError(String error);
    }
    public static void getUsuario(String id, final OnGetUsuario callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.getUsuario(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null && callback != null) {
                    if (response.body().has("error") && response.body().get("error").getAsBoolean() == false ){

                        Type type = new TypeToken<UsuarioModel>() {}.getType();
                        callback.onSuccess((UsuarioModel) new Gson().fromJson(response.body().get("data").toString(), type));

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




    public interface OnAlterarUsuario{
        void onSuccess(UsuarioModel usuario);
        void onError(String error);
    }
    public static void alterarUsuario(UsuarioModel usuario, final OnAlterarUsuario callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.alterarUsuario(usuario);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null && callback != null) {
                    if (response.body().has("error") && response.body().get("error").getAsBoolean() == false) {

                        Type type = new TypeToken<UsuarioModel>() {}.getType();
                        callback.onSuccess((UsuarioModel) new Gson().fromJson(response.body().get("data").toString(), type));

                    } else if (response.body().has("message")) {
                        callback.onError(response.body().get("message").toString());
                    } else {
                        callback.onError(ApsNoticiasApp.getInstance().getString(R.string.error_generico));
                    }
                } else {
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
