package com.noticias_now.services;

import com.google.gson.JsonObject;
import com.noticias_now.model.CurtidaModel;
import com.noticias_now.model.NoticiaModel;
import com.noticias_now.model.UsuarioModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ricarlo on 13/11/2016.
 */

public interface IApsNoticias {


    //String URL_BASE = "http://ec2-52-67-105-44.sa-east-1.compute.amazonaws.com:8080/MongoDBWebapp/";
    String URL_BASE = "http://201.52.174.54:8080/MongoDBWebapp/";

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

    @GET("noticia/getAllNewsPerson/{id_person}")
    Call<JsonObject> getAllNoticiasPerson(@Path("id_person") String id_person);

    @POST("noticia/curtir")
    Call<JsonObject> createLike(@Body CurtidaModel curtida);

    @POST("noticia/inserir")
    Call<JsonObject> inserirNoticia(@Body NoticiaModel usuario);

    @POST("noticia/alterar")
    Call<JsonObject> alterarNoticia(@Body NoticiaModel usuario);

    @POST("noticia/deletar")
    Call<JsonObject> deletarNoticia(@Body NoticiaModel usuario);

}
