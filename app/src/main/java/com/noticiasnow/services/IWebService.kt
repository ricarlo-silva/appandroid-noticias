package com.noticiasnow.services

import br.com.ricarlo.network.ApiResponse
import com.noticiasnow.model.LikeModel
import com.noticiasnow.model.NewsModel
import com.noticiasnow.model.UserModel
import com.noticiasnow.model.request.SessionRequest
import com.noticiasnow.model.response.TypeResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by ricarlo on 13/11/2016.
 */
interface IWebService {
    /*
    * ################ USUÁRIO #################
    * */
    @POST("usuario/inserir")
    suspend fun registerUser(@Body user: UserModel): ApiResponse<UserModel>

    @GET("usuario/{id}")
    suspend fun getUser(@Path("id") id: String): ApiResponse<UserModel>

    @POST("usuario/login")
    suspend fun login(@Body session: SessionRequest): ApiResponse<UserModel>

    @POST("usuario/alterar") // TODO refactor to @PATCH
    suspend fun updateUser(@Body user: UserModel): ApiResponse<UserModel>

    /*
    * ################ NOTÍCIAS #################
    * */
    @GET("noticia/getNewsById/{id}")
    suspend fun getNewsById(@Path("id") id: String): ApiResponse<NewsModel>

    @GET("noticia/getNewsByType/{type}")
    suspend fun getNewsByType(@Path("type") type: String): ApiResponse<List<NewsModel>>

    @GET("noticia/getAllNewsPerson/{idUser}")
    suspend fun getAllNewsPerson(@Path("idUser") idUser: String): ApiResponse<List<NewsModel>>

    @POST("noticia/curtir") // TODO return only likes
    suspend fun createLike(@Body like: LikeModel): ApiResponse<NewsModel>

    @POST("noticia/inserir")
    suspend fun insertNews(@Body news: NewsModel): ApiResponse<NewsModel>

    @POST("noticia/alterar") // TODO refactor to @PATCH
    suspend fun updateNews(@Body news: NewsModel): ApiResponse<NewsModel>

    @POST("noticia/deletar") // TODO send only id; change to @DELETE; return message success
    suspend fun deleteNews(@Body news: NewsModel)

    @GET("noticias/tipos") // TODO create
    suspend fun getTypes(): ApiResponse<List<TypeResponse>>
}
