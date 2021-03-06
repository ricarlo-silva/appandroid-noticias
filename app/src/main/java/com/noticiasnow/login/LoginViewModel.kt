package com.noticiasnow.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ricarlo.common.firebase.remoteconfig.IFirebaseRemoteConfigManager
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import br.com.ricarlo.common.util.resources.IResourcesManager
import com.noticiasnow.R
import com.noticiasnow.account.register.IUserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: IUserRepository,
    private val resourcesManager: IResourcesManager,
    private val dispatchers: ICoroutinesDispatcherProvider,
    private val firebaseRemoteConfigManager: IFirebaseRemoteConfigManager
) : ViewModel() {

    private val _user = MutableLiveData<ViewState<Unit>>()
    val user: LiveData<ViewState<Unit>> get() = _user

    fun login(email: String, password: String) {
        viewModelScope.launch(dispatchers.main()) {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                _user.postValue(ViewState.Loading())

                runCatching {
                    userRepository.login(
                        SessionQuery.SingIn(
                            email = email,
                            password = password,
                        )
                    )
                }.onSuccess {
                    _user.postValue(ViewState.Success(it))
                }.onFailure {
                    _user.postValue(ViewState.Error(error = it))
                }
            } else {
                _user.postValue(
                    ViewState.Error(
                        error = Exception(resourcesManager.getString(R.string.preencher_campos))
                    )
                )
            }
        }
    }
}
