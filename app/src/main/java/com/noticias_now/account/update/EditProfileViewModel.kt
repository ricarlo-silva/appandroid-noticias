package com.noticias_now.account.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import br.com.ricarlo.common.util.resources.IResourcesManager
import com.noticias_now.R
import com.noticias_now.account.register.IUserRepository
import com.noticias_now.model.UserModel
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val userRepository: IUserRepository,
    private val resourcesManager: IResourcesManager,
    private val dispatchers: ICoroutinesDispatcherProvider
) : ViewModel() {

    private val _user = MutableLiveData<ViewState<UserModel>>()
    val user: LiveData<ViewState<UserModel>> get() = _user

    private val _result = MutableLiveData<ViewState<Unit>>()
    val result: LiveData<ViewState<Unit>> get() = _result

    init {
        load()
    }

    private fun load() {
        _user.value = ViewState.Loading()
        viewModelScope.launch(dispatchers.main()) {
            runCatching {
                userRepository.get()
            }.onSuccess {
                _user.value = ViewState.Success(it)
            }.onFailure {
                _user.value = ViewState.Error(error = it)
            }
        }
    }

    fun update(name: String, email: String, password: String) {

        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            _result.value = ViewState.Loading()

            viewModelScope.launch(dispatchers.main()) {
                runCatching {
                    userRepository.update(
                        UserModel(
                            id = userRepository.get().id, // TODO refactor
                            name = name,
                            email = email,
                            password = password,
                            photo = null
                        )
                    )
                }.onSuccess {
                    _result.value = ViewState.Success(it)
                }.onFailure {
                    _result.value = ViewState.Error(error = it)
                }
            }
        } else {
            _result.value = ViewState.Error(error = Exception(resourcesManager.getString(R.string.preencher_campos)))
        }
    }
}
