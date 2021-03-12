package br.com.ricarlo.common.util.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutinesDispatcherProvider : ICoroutinesDispatcherProvider {
    override fun io(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun main(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun default(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    override fun unconfined(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }
}
