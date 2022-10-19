package com.noticiasnow.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * Created by ricarlo on 10/11/2016.
 */
@Keep
@Parcelize
data class UserModel(

    @Json(name = "id")
    val id: String? = null,

    @Json(name = "name")
    val name: String,

    @Json(name = "photo")
    val photo: String? = null,

    @Json(name = "email")
    val email: String,

    @Json(name = "token")
    val token: String? = null,

    @Json(name = "senha")
    val password: String? = null,
) : Parcelable
