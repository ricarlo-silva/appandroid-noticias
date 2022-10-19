package com.noticiasnow.details

import com.noticiasnow.model.LikeModel
import com.noticiasnow.model.NewsModel
import com.noticiasnow.model.TypeModel

interface INewsRepository {
    suspend fun getNewsById(id: String): NewsModel

    suspend fun createLike(like: LikeModel): NewsModel

    suspend fun getNewsByType(type: String): List<NewsModel>

    suspend fun getAllNewsPerson(idUser: String): List<NewsModel>

    suspend fun insertNews(news: NewsModel): NewsModel

    suspend fun updateNews(news: NewsModel): NewsModel

    suspend fun deleteNews(news: NewsModel)

    suspend fun getTypes(): List<TypeModel>
}
