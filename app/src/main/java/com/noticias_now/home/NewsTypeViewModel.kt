package com.noticias_now.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
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
        type?.let {
            if (_type.value != it) {
                _type.value = it
            }
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
