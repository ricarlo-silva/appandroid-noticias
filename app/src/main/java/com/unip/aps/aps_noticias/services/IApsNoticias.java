package com.unip.aps.aps_noticias.services;

import com.google.gson.JsonObject;

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


    String URL_BASE = "http://ec2-52-67-22-2.sa-east-1.compute.amazonaws.com:8080/";

    /*
    * ################ USUÁRIO #################
    * */

    @POST("/usuario/inserir")
    Call<JsonObject> inserirUsuario(@Body String usuario);

    @GET("/usuario/{id}")
    Call<JsonObject> getUsuario(@Path("id") long id);

    @POST("/usuario/login")
    Call<JsonObject> loginUsuario(@Body String usuario);

    @PUT("/usuario/alterar")
    Call<JsonObject> alterarUsuario(@Body String usuario);

    @DELETE("/usuario/excluir")
    Call<JsonObject> excluirUsuario(@Body String usuario);


    /*
    * ################ NOTÍCIAS #################
    * */

    @POST("/noticia/inserir")
    Call<JsonObject> inserirNoticia(@Body String usuario);

    //@GET("/noticia/{id}")
    //Call<JsonObject> getNoticia(@Path("id") long id);

    @GET("/noticia/{type}")
    Call<JsonObject> getNoticiaByType(@Path("type") long type);

    @PUT("/noticia/alterar")
    Call<JsonObject> alterarNoticia(@Body String usuario);

    @DELETE("/noticia/excluir")
    Call<JsonObject> excluirNoticia(@Body String usuario);

}
