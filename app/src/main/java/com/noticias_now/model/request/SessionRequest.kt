package com.noticias_now.model.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SessionRequest(
        @SerializedName("email")
        val email: String,

        @SerializedName("senha")
        val password: String
)