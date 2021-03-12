package com.noticiasnow.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import com.noticiasnow.account.register.IUserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SplashViewModel(
    private val userRepository: IUserRepository,
    private val dispatchers: ICoroutinesDispatcherProvider
) : ViewModel() {

    companion object {
        private const val TIME_DELAY: Long = 20
    }

    private var completedAnimation = false

    fun setCompletedAnimation() {
        completedAnimation = true
    }

    val result: LiveData<ViewState<SplashEvent>> = liveData(dispatchers.main()) {
        emit(ViewState.Loading<SplashEvent>())
        runCatching {
            userRepository.checkIfLogged()
        }.onSuccess { isLogged ->
            val event = ViewState.Success(
                if (isLogged) SplashEvent.LaunchHome
                else SplashEvent.LaunchLogin
            )
            while (completedAnimation.not()) {
                withContext(dispatchers.io()) { delay(TIME_DELAY) }
            }
            emit(event)
        }.onFailure {
            emit(ViewState.Error<SplashEvent>(it))
        }
    }
}
