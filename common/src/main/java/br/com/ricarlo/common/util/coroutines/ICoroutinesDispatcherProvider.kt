package br.com.ricarlo.common.util.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface ICoroutinesDispatcherProvider {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}
