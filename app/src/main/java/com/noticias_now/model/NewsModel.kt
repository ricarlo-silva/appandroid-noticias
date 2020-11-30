package com.noticias_now.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by ricarlo on 12/11/2016.
 */
@Keep
@Parcelize
data class NewsModel(

        @SerializedName("id")
        val id: String? = null,

        @SerializedName("person")
        val user: UserModel? = null,

        @SerializedName("titulo")
        val title: String,

        @SerializedName("tipo")
        val type: String,

        @SerializedName("descricao")
        val description: String,

        @SerializedName("data_publicacao")
        val publicationDate: String? = null,

        @SerializedName("votou")
        val isVoted: Boolean = false,

        @SerializedName("curtidas")
        val likes: List<LikeModel>? = null
) : Parcelable