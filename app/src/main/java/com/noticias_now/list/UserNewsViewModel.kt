package com.noticias_now.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import br.com.ricarlo.common.util.resources.IResourcesManager
import com.noticias_now.account.register.IUserRepository
import com.noticias_now.details.INewsRepository
import com.noticias_now.model.NewsModel
import kotlinx.coroutines.launch

class UserNewsViewModel(
        private val userRepository: IUserRepository,
        private val newsRepository: INewsRepository,
        private val resourcesManager: IResourcesManager,
        private val dispatchers: ICoroutinesDispatcherProvider
) : ViewModel() {

    private val _news = MutableLiveData<ViewState<List<NewsModel>>>()
    val news: LiveData<ViewState<List<NewsModel>>> get() = _news

    private val _delete = MutableLiveData<ViewState<NewsModel>>()
    val delete: LiveData<ViewState<NewsModel>> get() = _delete


    fun getNews() {
        _news.value = ViewState.Loading()
        viewModelScope.launch(dispatchers.main()) {
            runCatching {
                newsRepository.getAllNewsPerson(userRepository.get().id.orEmpty())
            }.onSuccess {
                _news.value = ViewState.Success(it)
            }.onFailure {
                _news.value = ViewState.Error(error = it)
            }
        }
    }

    fun deleteNews(newsModel: NewsModel) {
        _delete.value = ViewState.Loading()
        viewModelScope.launch(dispatchers.main()) {
            runCatching {
                newsRepository.deleteNews(newsModel)
            }.onSuccess {
                _delete.value = ViewState.Success(newsModel)
            }.onFailure {
                _delete.value = ViewState.Error(error = it)
            }
        }
    }

}