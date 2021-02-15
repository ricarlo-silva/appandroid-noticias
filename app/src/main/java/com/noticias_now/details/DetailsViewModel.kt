package com.noticias_now.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import com.noticias_now.account.register.IUserRepository
import com.noticias_now.model.LikeModel
import com.noticias_now.model.NewsModel
import kotlinx.coroutines.launch

class DetailsViewModel(
        private val userRepository: IUserRepository,
        private val newsRepository: INewsRepository,
        private val dispatchers: ICoroutinesDispatcherProvider
) : ViewModel() {

    private val _news = MutableLiveData<ViewState<NewsModel>>()
    val news: LiveData<ViewState<NewsModel>> get() = _news

    private val _like = MutableLiveData<ViewState<Triple<Int, Int, Boolean>>>()
    val like: LiveData<ViewState<Triple<Int, Int, Boolean>>> get() = _like

    fun getNewsById(news: NewsModel?) {
        if (news == null) return

        _news.value = ViewState.Success(news) //TODO review

        if (!news.id.isNullOrEmpty()) {
            _news.value = ViewState.Loading()

            viewModelScope.launch(dispatchers.main()) {
                runCatching {
                    val result = newsRepository.getNewsById(news.id)
                    val userId = userRepository.get().id
                    val likes = getLikes(result.likes.orEmpty(), userId)
                    Pair(result, likes) // TODO review
                }.onSuccess {
                    _news.value = ViewState.Success(it.first)
                    _like.value = ViewState.Success(it.second)
                }.onFailure {
                    _news.value = ViewState.Error(error = it)
                }
            }

        } else {
            _news.value = ViewState.Error(error = RuntimeException())
        }
    }

    fun createLike(like: Boolean, idNews: String?) {
        if (idNews.isNullOrEmpty()) return

        _like.value = ViewState.Loading()

        viewModelScope.launch(dispatchers.main()) {
            runCatching {
                val userId = userRepository.get().id
                val news = newsRepository.createLike(LikeModel(
                        idUser = userId.orEmpty(),
                        idNews = idNews,
                        like = like
                ))
                getLikes(news.likes.orEmpty(), userId)
            }.onSuccess {
                _like.value = ViewState.Success(it)
            }.onFailure {
                _like.value = ViewState.Error(error = it)
            }
        }

    }

    private fun getLikes(likes: List<LikeModel>, userId: String?): Triple<Int, Int, Boolean> {
        var like = 0
        var unlike = 0
        var currentUserLike = false
        likes.forEach {
            if (it.like) like++ else unlike++

            if (currentUserLike.not() && it.idUser == userId) {
                currentUserLike = true
            }
        }
        return Triple(like, unlike, currentUserLike)
    }

}