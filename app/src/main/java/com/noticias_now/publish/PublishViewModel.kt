package com.noticias_now.publish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import br.com.ricarlo.common.util.resources.IResourcesManager
import com.noticias_now.R
import com.noticias_now.account.register.IUserRepository
import com.noticias_now.details.INewsRepository
import com.noticias_now.model.NewsModel
import kotlinx.coroutines.launch

class PublishViewModel(
    private val userRepository: IUserRepository,
    private val newsRepository: INewsRepository,
    private val resourcesManager: IResourcesManager,
    private val dispatchers: ICoroutinesDispatcherProvider
) : ViewModel() {

    private val _news = MutableLiveData<NewsModel?>()
    val news: LiveData<NewsModel?> get() = _news

    private val _result = MutableLiveData<ViewState<String>>()
    val result: LiveData<ViewState<String>> get() = _result

    private var _type = ""

    fun setType(type: String) {
        _type = type
    }

    fun setNews(news: NewsModel?) {
        _news.value = news
    }

    fun insertOrUpdateNews(title: String, description: String) {

        if (title.isNotEmpty() && description.isNotEmpty() && _type.isNotEmpty()) {

            val message = if (isUpdate()) R.string.atualizando_news else R.string.publicando
            _result.value = ViewState.Loading(resourcesManager.getString(message))
            viewModelScope.launch(dispatchers.main()) {
                runCatching {
                    val model = NewsModel(
                        title = title,
                        type = _type,
                        description = description,
                        user = userRepository.get()
                    )
                    if (isUpdate()) {
                        newsRepository.updateNews(model)
                    } else {
                        newsRepository.insertNews(model)
                    }
                }.onSuccess {
                    val success = if (isUpdate()) R.string.atualizando_news_sucesso else R.string.sucesso_publicao
                    _result.value = ViewState.Success(resourcesManager.getString(success))
                }.onFailure {
                    _result.value = ViewState.Error(it)
                }
            }
        } else {
            _result.value = ViewState.Error(error = Exception(resourcesManager.getString(R.string.preencher_campos)))
        }
    }

    private fun isUpdate() = _news.value != null
}
