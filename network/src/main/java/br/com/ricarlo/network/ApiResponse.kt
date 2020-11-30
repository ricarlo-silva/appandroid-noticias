package br.com.ricarlo.network

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiResponse<T>(
        @field:SerializedName("error")
        val error: Boolean,

        @field:SerializedName("data")
        val data: T
)