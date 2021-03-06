package com.noticiasnow.account.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.ricarlo.common.ui.BaseViewModel
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import br.com.ricarlo.common.util.resources.IResourcesManager
import com.noticiasnow.R
import com.noticiasnow.model.UserModel
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: IUserRepository,
    private val resourcesManager: IResourcesManager,
    private val dispatchers: ICoroutinesDispatcherProvider
) : BaseViewModel() {

    private val _user = MutableLiveData<ViewState<Unit>>()
    val user: LiveData<ViewState<Unit>> get() = _user

    fun insert(name: String, email: String, password: String, photo: String) {

        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            _user.value = ViewState.Loading()

            viewModelScope.launch(dispatchers.main()) {
                runCatching {
                    userRepository.insert(
                        UserModel(
                            name = name,
                            email = email,
                            password = password,
                            photo = photo
                        )
                    )
                }.onSuccess {
                    _user.value = ViewState.Success(Unit)
                }.onFailure {
                    _user.value = ViewState.Error(error = it)
                }
            }
        } else {
            _user.value = ViewState.Error(error = Exception(resourcesManager.getString(R.string.preencher_campos)))
        }
    }
}
