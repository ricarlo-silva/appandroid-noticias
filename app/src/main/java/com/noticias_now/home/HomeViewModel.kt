package com.noticias_now.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.ricarlo.common.ui.BaseViewModel
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import com.noticias_now.details.INewsRepository
import com.noticias_now.model.TypeModel
import kotlinx.coroutines.launch

class HomeViewModel(
        private val newsRepository: INewsRepository,
        private val dispatchers: ICoroutinesDispatcherProvider
) : BaseViewModel() {

    private val _types = MutableLiveData<ViewState<List<TypeModel>>>()
    val types: LiveData<ViewState<List<TypeModel>>> get() = _types

    fun get() {
        viewModelScope.launch(dispatchers.main()) {
            _types.value = ViewState.Loading()
            runCatching {
                newsRepository.getTypes()
            }.onSuccess {
                _types.value = ViewState.Success(it)
            }.onFailure {
                _types.value = ViewState.Error(it)
            }
        }
    }

}