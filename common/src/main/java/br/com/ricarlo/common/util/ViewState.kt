package br.com.ricarlo.common.util

sealed class ViewState<T> {
    class Success<T>(val data: T) : ViewState<T>()
    class Loading<T>(val message: String? = null) : ViewState<T>()
    class Error<T>(val error: Throwable, retry: Runnable? = null) : ViewState<T>()
}
