package com.noticias_now.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.ricarlo.common.util.ViewState
import com.noticias_now.account.register.IUserRepository
import kotlinx.coroutines.delay

class SplashViewModel(
        private val userRepository: IUserRepository
) : ViewModel() {

    companion object {
        private const val TIME_DELAY: Long = 3000
    }

    val result : LiveData<ViewState<SplashEvent>> = liveData {
        emit(ViewState.Loading<SplashEvent>())
        delay(TIME_DELAY)
        runCatching {
            userRepository.checkIfLogged()
        }.onSuccess { isLogged ->
            val event = ViewState.Success(
                    if (isLogged) SplashEvent.LaunchHome
                    else SplashEvent.LaunchLogin
            )
            emit(event)
        }.onFailure {
            emit(ViewState.Error<SplashEvent>(it))
        }
    }
}