package com.noticias_now.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class TypeResponse(
    @Json(name = "id")
    val id: String,

    @Json(name = "name")
    val name: String
)
