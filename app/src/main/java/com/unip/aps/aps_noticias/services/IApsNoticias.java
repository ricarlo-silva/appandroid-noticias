package com.unip.aps.aps_noticias.services;

import com.google.gson.JsonObject;
import com.unip.aps.aps_noticias.model.CurtidaModel;
import com.unip.aps.aps_noticias.model.UsuarioModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ricarlo on 13/11/2016.
 */

public interface IApsNoticias {


    //String URL_BASE = "http://ec2-52-67-22-2.sa-east-1.compute.amazonaws.com:8080/MongoDBWebapp/";
    String URL_BASE = "http://192.168.0.102:8080/MongoDBWebapp/";

    /*
    * ################ USUÁRIO #################
    * */

    @POST("usuario/inserir")
    Call<JsonObject> inserirUsuario(@Body UsuarioModel usuario);

    @GET("usuario/{id}")
    Call<JsonObject> getUsuario(@Path("id") String id);

    @POST("usuario/login")
    Call<JsonObject> loginUsuario(@Body UsuarioModel usuario);

    @POST("usuario/alterar")
    Call<JsonObject> alterarUsuario(@Body UsuarioModel usuario);


    /*
    * ################ NOTÍCIAS #################
    * */

    @GET("noticia/getNewsById/{id}")
    Call<JsonObject> getNoticiaById(@Path("id") String id);

    @GET("noticia/getNewsByType/{type}")
    Call<JsonObject> getNoticiaByType(@Path("type") String type);

    @POST("noticia/curtir")
    Call<JsonObject> createLike(@Body CurtidaModel curtida);

}
