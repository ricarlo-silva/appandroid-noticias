package com.unip.aps.aps_noticias.app;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import com.unip.aps.aps_noticias.model.UsuarioModel;
import com.unip.aps.aps_noticias.util.ApsNoticiasUtils;

/**
 * Created by ricarlo on 10/11/2016.
 */

public class ApsNoticiasApp extends Application {

    private static ApsNoticiasApp instance;
    private UsuarioModel usuario;
    public static Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        verifyExistUser();
    }

    public static ApsNoticiasApp getInstance(){
        return instance;
    }

    public UsuarioModel getUsuario(){
        return usuario;
    }

    public void verifyExistUser(){
        String jsonUser = ApsNoticiasUtils.read(instance, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DATA_USER, null);
        if (!TextUtils.isEmpty(jsonUser)){
            usuario = (UsuarioModel) ApsNoticiasUtils.jsonToObject(jsonUser, UsuarioModel.class);
        }
    }

    public boolean checkIfLoggedin() {
        return (usuario != null) ? true : false;
    }

    public void saveUser(UsuarioModel user){
        if (user != null){
            ApsNoticiasUtils.write(instance, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DATA_USER, ApsNoticiasUtils.objectToString(user));
            usuario = user;
        }
    }

    public void doLogout() {
        usuario = null;
        saveUserInfo("");
        ApsNoticiasUtils.write(this, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DEVICE_TOKEN, "");
        ApsNoticiasUtils.remove(this, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DEVICE_TOKEN);

    }

    private static void saveUserInfo(String userJSON) {
        ApsNoticiasUtils.write(instance, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DATA_USER, userJSON);
    }
}
