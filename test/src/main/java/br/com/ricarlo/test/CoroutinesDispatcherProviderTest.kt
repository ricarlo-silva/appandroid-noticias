package br.com.ricarlo.test

import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class CoroutinesDispatcherProviderTest(
        private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : ICoroutinesDispatcherProvider {

    override fun default(): CoroutineDispatcher = testDispatcher
    override fun io(): CoroutineDispatcher = testDispatcher
    override fun main(): CoroutineDispatcher = testDispatcher
    override fun unconfined(): CoroutineDispatcher = testDispatcher

}