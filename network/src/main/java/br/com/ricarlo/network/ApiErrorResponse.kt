package br.com.ricarlo.network

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ApiErrorResponse(

    @Json(name = "message")
    val message: String? = null
)
