package com.unip.aps.aps_noticias.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ricarlo on 19/11/2016.
 */

public class CurtidaModel implements Serializable {

    @SerializedName("id_person")
    @Expose
    private String idPerson;
    @SerializedName("id_news")
    @Expose
    private String idNews;
    @SerializedName("like")
    @Expose
    private Boolean like;

    /**
     *
     * @return
     * The idPerson
     */
    public String getIdPerson() {
        return idPerson;
    }

    /**
     *
     * @param idPerson
     * The id_person
     */
    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }

    /**
     *
     * @return
     * The idNews
     */
    public String getIdNews() {
        return idNews;
    }

    /**
     *
     * @param idNews
     * The id_news
     */
    public void setIdNews(String idNews) {
        this.idNews = idNews;
    }

    /**
     *
     * @return
     * The like
     */
    public Boolean getLike() {
        return like;
    }

    /**
     *
     * @param like
     * The like
     */
    public void setLike(Boolean like) {
        this.like = like;
    }

}
