package com.noticiasnow.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.ricarlo.common.di.CommonModule
import br.com.ricarlo.common.firebase.remoteconfig.IFirebaseRemoteConfigManager
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import br.com.ricarlo.common.util.resources.IResourcesManager
import br.com.ricarlo.network.ApiException
import br.com.ricarlo.test.CoroutineTestRule
import br.com.ricarlo.test.test
import com.noticiasnow.account.register.IUserRepository
import com.noticiasnow.di.AppModule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import org.koin.test.KoinTestRule
import org.koin.test.inject

// import org.robolectric.annotation.Config

@KoinApiExtension
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
// @Config(sdk = [Build.VERSION_CODES.P])
class LoginViewModelTest : BaseTestCase() {

    private val viewModel: LoginViewModel by inject()
    private val userRepository: IUserRepository by inject()
    private val resourcesManager: IResourcesManager by inject()
    private val mContext: Context by inject()
    private val firebaseRemoteConfigManager: IFirebaseRemoteConfigManager by inject()

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    var koinTestRule = KoinTestRule.create {
        modules(
            AppModule.modules + CommonModule.modules + module(override = true) {
                factory { mockk<Context>(relaxed = true) }
                single { mockk<IUserRepository>(relaxUnitFun = true) }
                single { mockk<IFirebaseRemoteConfigManager>(relaxUnitFun = true) }
                single<ICoroutinesDispatcherProvider> {
                    coroutinesTestRule.testDispatcherProvider
                }
            }
        )
    }

//    @Test
//    fun test() = coroutinesTestRule.runBlockingTest {
//        viewModel.login("", "")
//        coroutinesTestRule.testDispatcher.pauseDispatcher()
//        viewModel.user.observeForTesting {
//            assertLoading(viewModel.user.value!!, null)
//            coroutinesTestRule.testDispatcher.resumeDispatcher()
//            assertError(viewModel.user.value!!, null)
//        }
//
//    }

    @Test
    fun givenWebserviceSuccess_whenCallLoginWithValidEmailAndPassword_thenShowLoadingAndSuccess() {
        coroutinesTestRule.runBlockingTest {

            // given
            coEvery {
                userRepository.login(
                    match { it.email == "test@email.com" && it.password == "123456" }
                )
            } just Runs

            every {
                firebaseRemoteConfigManager
                    .fetchSync("welcome_message", String::class.java)
            } returns "nk"

            val mockObserver = viewModel.user.test()

            // when
            viewModel.login("test@email.com", "123456")

            // then
            coVerifySequence {
                mockObserver.onChanged(match { it is ViewState.Loading })
                mockObserver.onChanged(match { it is ViewState.Success })
            }

            coVerify(exactly = 0) {
                mockObserver.onChanged(match { it is ViewState.Error })
            }

            confirmVerified(mockObserver)
        }
    }

    @Test
    fun givenWebserviceError_whenCallLogin_thenShowLoadingAndError() {
        coroutinesTestRule.runBlockingTest {

            // given
            coEvery {
                userRepository.login(
                    match { it.email == "test@email.com" && it.password == "123456" }
                )
            } throws ApiException(statusCode = 500, message = null)

            val mockObserver = viewModel.user.test()

            // when
            viewModel.login("test@email.com", "123456")

            // then
            coVerifySequence {
                mockObserver.onChanged(match { it is ViewState.Loading })
                mockObserver.onChanged(
                    match {
                        (it is ViewState.Error) && (it.error as ApiException).statusCode == 500
                    }
                )
            }

            coVerify(exactly = 0) {
                mockObserver.onChanged(match { it is ViewState.Success })
            }

            confirmVerified(mockObserver)
        }
    }

    @Test
    fun givenMockedResources_whenCallLoginWithEmptyEmailAndPassword_thenShowError() {
        // given
        val mockObserver = viewModel.user.test()
        every { resourcesManager.getString(any()) } returns "Preencha todos os campos."

        // when
        viewModel.login("", "")

        // then
        val slots = mutableListOf<ViewState<Unit>>()
        verify {
            mockObserver.onChanged(capture(slots))
        }

        assertError(slots[0], "Preencha todos os campos.")
    }
}
