package com.noticias_now.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TypeResponse(
        @SerializedName("id")
        val id: String,

        @SerializedName("name")
        val name: String
)
