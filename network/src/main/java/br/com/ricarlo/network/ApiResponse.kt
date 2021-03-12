package br.com.ricarlo.network

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ApiResponse<T>(
    @Json(name = "error")
    val error: Boolean = false,

    @Json(name = "data")
    val data: T
)
