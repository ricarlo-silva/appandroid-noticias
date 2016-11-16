package com.unip.aps.aps_noticias.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.unip.aps.aps_noticias.R;
import com.unip.aps.aps_noticias.app.ApsNoticiasApp;
import com.unip.aps.aps_noticias.model.NoticiaModel;
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
    public static void getNoticiasByType(long type, final OnGetNotices callback){

        if (!ApsNoticiasUtils.isOnline(ApsNoticiasApp.getInstance())){
            callback.onError(ApsNoticiasApp.getInstance().getString(R.string.sem_conexao));
            return;
        }

        iApsNoticias = ServiceGenerator.createService(IApsNoticias.class);
        Call<JsonObject> call = iApsNoticias.getNoticiaByType(type);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response != null && callback != null) {
                    if (response.code() == 200) {
                        Type type = new TypeToken<List<NoticiaModel>>() {}.getType();
                        callback.onSuccess((List<NoticiaModel>) new Gson().fromJson(response.body().toString(), type));
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
