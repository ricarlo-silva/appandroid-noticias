package com.noticias_now.details

import com.noticias_now.model.LikeModel
import com.noticias_now.model.NewsModel
import com.noticias_now.model.TypeModel

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
