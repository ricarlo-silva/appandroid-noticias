package com.noticias_now.login

import br.com.ricarlo.common.util.ViewState
import com.google.common.truth.Truth
import junit.framework.TestCase
import org.koin.core.module.Module
import org.koin.test.KoinTest

abstract class BaseTestCase : KoinTest {

    open fun testModules() = listOf<Module>()


    fun assertError(result: ViewState<*>, throwable: String?) {
        //        assertTrue(result is ViewState.Error)
//
//        assertThat(result, instanceOf(ViewState.Error::class.java))
        Truth.assertThat(result is ViewState.Error).isTrue()
        Truth.assertThat((result as ViewState.Error).error.message).isEqualTo(throwable)
    }

    fun assertLoading(result: ViewState<*>, message: String? = null) {
        Truth.assertThat(result is ViewState.Loading).isTrue()
        if (message.isNullOrEmpty()) {
            Truth.assertThat((result as ViewState.Loading).message).isEqualTo(message)
        }
    }

    fun <T> assertSuccess(result: ViewState<T>, data: T) {
        Truth.assertThat(result is ViewState.Success).isTrue()
        Truth.assertThat((result as ViewState.Success).data).isEqualTo(data)
    }
}