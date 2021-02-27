package br.com.ricarlo.network

// import br.com.ricarlo.common.util.test.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend inline fun <T> apiCall(dispatcher: CoroutineDispatcher, crossinline block: suspend () -> T): T {
//    return wrapEspressoIdlingResource {
    return withContext(dispatcher) {
        try {
            block()
        } catch (throwable: Throwable) {
            throw when (throwable) {
                is HttpException -> {
                    val errorResponse =
                        fromJson<ApiErrorResponse>(throwable.response()?.errorBody()?.string())

                    ApiException(
                        statusCode = throwable.code(),
                        message = errorResponse?.message
                    )
                }
                else -> {
                    throwable
                }
            }
        }
    }
//    }
}
