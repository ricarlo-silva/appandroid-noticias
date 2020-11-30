package com.noticias_now.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by ricarlo on 19/11/2016.
 */
@Keep
@Parcelize
data class LikeModel(

        @SerializedName("id_person")
        val idUser: String,

        @SerializedName("id_news")
        val idNews: String,

        @SerializedName("like")
        val like: Boolean
) : Parcelable