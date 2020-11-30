package br.com.ricarlo.network

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiErrorResponse(

	@field:SerializedName("message")
	val message: String? = null
)