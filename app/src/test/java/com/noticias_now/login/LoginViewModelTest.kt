package com.noticias_now.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.ricarlo.common.di.CommonModule
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import br.com.ricarlo.common.util.resources.IResourcesManager
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
import org.mockito.Mockito
import org.mockito.Mockito.*


//import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
//@Config(sdk = [Build.VERSION_CODES.P])
class LoginViewModelTest : BaseTestCase() {

    private val viewModel: LoginViewModel by inject()
    private val userRepository: IUserRepository by inject()
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
    fun login_with_empty_email_and_password() {

        // given
        every { resourcesManager.getString(any()) } returns "Preencha todos os campos."
        val observer = viewModel.user.test()
//
//        // when
//        viewModel.login("", "")
//
//        // then
//        observer.values { states ->
////            assertLoading(states[0])
//            assertError(states[0], "Preencha todos os campos.")
//        }.assertValueCount(1)


//        Mockito.verify(observer, Mockito.times(2))
//                .onChanged(argumentCaptor.capture())
// then
        val slots = mutableListOf<ViewState<Unit>>()
        verify {
            observer.onChanged(capture(slots))
        }

        viewModel.user.observeForever(observer)
        viewModel.login("", "")
//        val values = slots.getAllValues();

//        Assert.assertEquals(Status.LOADING,values.get(0).status)
//
//        Assert.assertEquals(Status.ERROR, values.get(1).status)


    }

    @Test
    fun login_with_valid_email_and_password() = coroutinesTestRule.runBlockingTest {

        coEvery { userRepository.login(any()) } returns Unit

        // given
        val mockObserver = viewModel.user.test()

        // when
        viewModel.login("test@email.com", "123456")

        // then
//        observer.values { states ->
//            assertLoading(states[0])
//            assertSuccess(states[1], Unit)
//        }.assertValueCount(2)

        coVerifySequence {
            mockObserver.onChanged(any())
            mockObserver.onChanged(any())

        }



//
//        verify(mockObserver).onChanged(isA(Loading::class.java))
//        verify(mockObserver).onChanged(isA(State.Success::class.java))
//
//        verify(mockObserver, times(1)).onChanged(isA(ViewState.Loading<Unit>::class.java))
//        verify(mockObserver, times(1)).onChanged(isA(State.Success::class.java))
//
//        verify(mockObserver, never()).onChanged(isA(ViewState.Error::class.java))
//
//        verifyNoMoreInteractions(mockObserver)?


    }

    @Test
    fun test1() {
        // given
        val observer = mockk<Observer<ViewState<Unit>>> { every { onChanged(any()) } just  Runs }
        viewModel.user.observeForever(observer)
        every { resourcesManager.getString(any()) } returns "Preencha todos os campos."


        // when
        viewModel.login("test@email.com", "123456")


        // then
        val slots = mutableListOf<ViewState<Unit>>()
        verify {
            observer.onChanged(capture(slots))
        }

        assertLoading(slots[0])
        assertSuccess(slots[1], Unit)

    }
//
//    @Test
//    fun login_with_empty_email_and_password() = coroutinesTestRule.runBlockingTest {
//        // Given
//        val observer = mockk<Observer<ViewState<Unit>>> { every { onChanged(any()) } just Runs }
//
//        viewModel.user.observeForever(observer)
//
////        val slot = slot<ViewState<*>>()
//        every { resourcesManager.getString(any()) } returns "Preencha todos os campos."
//
//        // When
//        viewModel.login("", "")
//
//        // Then
////        coVerify(exactly = 0) { userRepository.login(SessionQuery.SingIn("", "")) }
//
//        val expected = Exception("Preencha todos os campos.")
////        assertEquals(expected, viewModel.user.getOrAwaitValue())
//
////        assertThat(expected, equalTo(viewModel.user.getOrAwaitValue()))
//
////        val result = viewModel.user.getOrAwaitValue()
//
//
////        assertError(result, expected.message)
//
//        verifySequence {
//            observer.onChanged(any())
//            observer.onChanged(any())
////
////            observer.onChanged(eq(ViewState.Loading()))
////            observer.onChanged(eq(ViewState.Error(expected)))
//        }
//
//    }


}