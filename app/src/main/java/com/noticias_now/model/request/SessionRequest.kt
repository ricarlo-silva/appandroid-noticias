package com.noticias_now.model.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class SessionRequest(
    @Json(name = "email")
    val email: String,

    @Json(name = "senha")
    val password: String
)
