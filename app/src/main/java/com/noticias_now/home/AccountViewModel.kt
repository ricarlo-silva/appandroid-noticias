package com.noticias_now.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import br.com.ricarlo.common.ui.BaseViewModel
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import com.noticias_now.account.register.IUserRepository
import kotlinx.coroutines.launch

class AccountViewModel(
    private val userRepository: IUserRepository,
    private val dispatchers: ICoroutinesDispatcherProvider
) : BaseViewModel() {

    private val _logout = MutableLiveData<ViewState<Unit>>()
    val logout: LiveData<ViewState<Unit>> get() = _logout

    val user = liveData {
        emit(userRepository.get())
    }

    fun logout() {
        viewModelScope.launch(dispatchers.main()) {
            _logout.value = ViewState.Loading()
            runCatching {
                userRepository.logout()
            }.onSuccess {
                _logout.value = ViewState.Success(it)
            }.onFailure {
                _logout.value = ViewState.Error(it)
            }
        }
    }
}
