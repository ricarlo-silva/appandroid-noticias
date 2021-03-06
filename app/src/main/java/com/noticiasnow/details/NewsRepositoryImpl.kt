package com.noticiasnow.details

import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import br.com.ricarlo.network.apiCall
import com.noticiasnow.model.LikeModel
import com.noticiasnow.model.NewsModel
import com.noticiasnow.model.TypeModel
import com.noticiasnow.model.mapper.TypeMapperImpl
import com.noticiasnow.services.IWebService

class NewsRepositoryImpl(
    private val remote: IWebService,
    private val dispatchers: ICoroutinesDispatcherProvider
) : INewsRepository {

    override suspend fun getNewsById(id: String): NewsModel {
        return apiCall(dispatchers.io()) {
            remote.getNewsById(id).data
        }
    }

    override suspend fun createLike(like: LikeModel): NewsModel {
        return apiCall(dispatchers.io()) {
            remote.createLike(like).data
        }
    }

    override suspend fun getNewsByType(type: String): List<NewsModel> {
        return apiCall(dispatchers.io()) {
            remote.getNewsByType(type).data
        }
    }

    override suspend fun getAllNewsPerson(idUser: String): List<NewsModel> {
        return apiCall(dispatchers.io()) {
            remote.getAllNewsPerson(idUser).data
        }
    }

    override suspend fun insertNews(news: NewsModel): NewsModel {
        return apiCall(dispatchers.io()) {
            remote.insertNews(news).data
        }
    }

    override suspend fun updateNews(news: NewsModel): NewsModel {
        return apiCall(dispatchers.io()) {
            remote.updateNews(news).data
        }
    }

    override suspend fun deleteNews(news: NewsModel) {
        return apiCall(dispatchers.io()) {
            remote.deleteNews(news)
        }
    }

    override suspend fun getTypes(): List<TypeModel> {
        return apiCall(dispatchers.io()) {
            val response = remote.getTypes().data
            TypeMapperImpl().toDomain(response)
        }
    }
}
