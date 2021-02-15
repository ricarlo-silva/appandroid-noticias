package com.noticias_now.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Created by ricarlo on 19/11/2016.
 */
@Keep
@Parcelize
data class LikeModel(

        @Json(name = "id_person")
        val idUser: String,

        @Json(name = "id_news")
        val idNews: String,

        @Json(name = "like")
        val like: Boolean
) : Parcelable