package com.noticiasnow.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * Created by ricarlo on 12/11/2016.
 */
@Keep
@Parcelize
data class NewsModel(

    @Json(name = "id")
    val id: String? = null,

    @Json(name = "person")
    val user: UserModel? = null,

    @Json(name = "titulo")
    val title: String,

    @Json(name = "tipo")
    val type: String,

    @Json(name = "descricao")
    val description: String,

    @Json(name = "data_publicacao")
    val publicationDate: String? = null,

    @Json(name = "votou")
    val isVoted: Boolean = false,

    @Json(name = "curtidas")
    val likes: List<LikeModel>? = null
) : Parcelable
