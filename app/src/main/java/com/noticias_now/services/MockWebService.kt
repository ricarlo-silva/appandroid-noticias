package com.noticias_now.services

import br.com.ricarlo.network.ApiResponse
import com.noticias_now.model.*
import com.noticias_now.model.request.SessionRequest
import com.noticias_now.model.response.TypeResponse
import kotlinx.coroutines.delay

class MockWebService : IWebService {

    private suspend fun <T> createMockResponse(data: T): ApiResponse<T> {
        delay(200)
        return ApiResponse(
                error = false,
                data = data
        )
    }

    override suspend fun registerUser(user: UserModel): ApiResponse<UserModel> {
        return createMockResponse(UserModel(
                id = "1",
                name = "test",
                email = "test@email.com"
        ))
    }

    override suspend fun getUser(id: String): ApiResponse<UserModel> {
        return createMockResponse(UserModel(
                id = "1",
                name = "test",
                email = "test@email.com"
        ))
    }

    override suspend fun login(session: SessionRequest): ApiResponse<UserModel> {
        return createMockResponse(UserModel(
                id = "1",
                name = "test",
                email = "test@email.com"
        ))
    }

    override suspend fun updateUser(user: UserModel): ApiResponse<UserModel> {
        return createMockResponse(UserModel(
                id = "1",
                name = "test",
                email = "test@email.com"
        ))
    }

    override suspend fun getNewsById(id: String): ApiResponse<NewsModel> {
        return createMockResponse(NewsModel(
                id = "1",
                title = "test",
                description = "test@email.com",
                type = "1",
                publicationDate = "",
                user = UserModel(
                        id = "1",
                        name = "test",
                        email = "test@email.com"
                ),
                likes = listOf()
        ))
    }

    override suspend fun getNewsByType(type: String): ApiResponse<List<NewsModel>> {
        val list = mutableListOf<NewsModel>().apply {
            repeat(10) {
                add(
                        NewsModel(
                                id = "$it",
                                title = "test $type",
                                description = "test@email.com",
                                type = type,
                                publicationDate = "",
                                user = UserModel(
                                        id = "1",
                                        name = "test",
                                        email = "test@email.com"
                                ),
                                likes = listOf()
                        )
                )
            }
        }
        return createMockResponse(list)
    }

    override suspend fun getAllNewsPerson(idUser: String): ApiResponse<List<NewsModel>> {
        return createMockResponse(
                listOf(
                        NewsModel(
                                id = "1",
                                title = "test",
                                description = "test@email.com",
                                type = "1",
                                publicationDate = "",
                                user = UserModel(
                                        id = "1",
                                        name = "test",
                                        email = "test@email.com"
                                ),
                                likes = listOf()
                        )))
    }

    override suspend fun createLike(like: LikeModel): ApiResponse<NewsModel> {
        return createMockResponse(NewsModel(
                id = "1",
                title = "test",
                description = "test@email.com",
                type = "1",
                publicationDate = "",
                user = UserModel(
                        id = "1",
                        name = "test",
                        email = "test@email.com"
                ),
                likes = listOf()
        ))
    }

    override suspend fun insertNews(news: NewsModel): ApiResponse<NewsModel> {
        return createMockResponse(NewsModel(
                id = "1",
                title = "test",
                description = "test@email.com",
                type = "1",
                publicationDate = "",
                user = UserModel(
                        id = "1",
                        name = "test",
                        email = "test@email.com"
                ),
                likes = listOf()
        ))
    }

    override suspend fun updateNews(news: NewsModel): ApiResponse<NewsModel> {
        return createMockResponse(NewsModel(
                id = "1",
                title = "test",
                description = "test@email.com",
                type = "1",
                publicationDate = "",
                user = UserModel(
                        id = "1",
                        name = "test",
                        email = "test@email.com"
                ),
                likes = listOf()
        ))
    }

    override suspend fun deleteNews(news: NewsModel) {
    }

    override suspend fun getTypes(): ApiResponse<List<TypeResponse>> {
        return createMockResponse(
                listOf(
                        TypeResponse("1", "Poluição do ar"),
                        TypeResponse("2", "Trânsito"),
                        TypeResponse("3", "Desmatamento")
                )
        )
    }
}