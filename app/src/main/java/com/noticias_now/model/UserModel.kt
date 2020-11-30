package com.noticias_now.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by ricarlo on 10/11/2016.
 */
@Keep
@Parcelize
data class UserModel(

        @SerializedName("id")
        val id: String? = null,

        @SerializedName("name")
        val name: String,

        @SerializedName("photo")
        val photo: String? = null,

        @SerializedName("email")
        val email: String,

        @SerializedName("token")
        val token: String? = null,

        @SerializedName("senha")
        val password: String? = null,
) : Parcelable