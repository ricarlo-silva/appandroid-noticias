package com.noticias_now.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.ricarlo.common.di.CommonModule
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import br.com.ricarlo.common.util.resources.IResourcesManager
import br.com.ricarlo.network.ApiException
import br.com.ricarlo.test.CoroutineTestRule
import br.com.ricarlo.test.test
import com.noticias_now.account.register.IUserRepository
import com.noticias_now.di.AppModule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.dsl.module
import org.koin.test.KoinTestRule
import org.koin.test.inject

//import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
//@Config(sdk = [Build.VERSION_CODES.P])
class LoginViewModelTest : BaseTestCase() {

    private val viewModel: LoginViewModel by inject()
    private val userRepository = mockk<IUserRepository>(relaxUnitFun = true)
    private val resourcesManager: IResourcesManager by inject()
    private val mContext = mockk<Context>(relaxed = true)

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    //    Sets the main coroutines dispatcher to a TestCoroutineScope for unit testing.
//    @ExperimentalCoroutinesApi
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var koinTestRule = KoinTestRule.create {
        modules(AppModule.modules + CommonModule.modules + module(override = true) {
            factory<Context> { mContext }
            single<IUserRepository> { userRepository }
            single<ICoroutinesDispatcherProvider> {
                coroutinesTestRule.testDispatcherProvider
            }
        })
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
                mockObserver.onChanged(match { (it is ViewState.Error) && (it.error as ApiException).statusCode == 500 })
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