package com.noticias_now.home

import androidx.lifecycle.*
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import com.noticias_now.details.INewsRepository
import com.noticias_now.model.NewsModel

class NewsTypeViewModel(
        private val newsRepository: INewsRepository,
        private val dispatchers: ICoroutinesDispatcherProvider
) : ViewModel() {

    private val _type = MutableLiveData<String>()

    fun setType(type: String?) {
        if (_type.value != type && !type.isNullOrEmpty()) {
            _type.value = type
        }
    }

    val news: LiveData<ViewState<List<NewsModel>>> = _type.switchMap {
        liveData<ViewState<List<NewsModel>>>(dispatchers.main()) {
            emit(ViewState.Loading())
            runCatching {
                newsRepository.getNewsByType(it)
            }.onSuccess {
                emit(ViewState.Success(it))
            }.onFailure {
                emit(ViewState.Error(it))
            }
        }
    }
}