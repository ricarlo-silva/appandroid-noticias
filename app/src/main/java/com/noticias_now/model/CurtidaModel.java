package com.noticias_now.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ricarlo on 19/11/2016.
 */

public class CurtidaModel implements Serializable {

    @SerializedName("id_person")
    @Expose
    private String id_person;
    @SerializedName("id_news")
    @Expose
    private String id_news;
    @SerializedName("like")
    @Expose
    private Boolean like;

    /**
     *
     * @return
     * The id_person
     */
    public String getId_person() {
        return id_person;
    }

    /**
     *
     * @param id_person
     * The id_person
     */
    public void setId_person(String id_person) {
        this.id_person = id_person;
    }

    /**
     *
     * @return
     * The id_news
     */
    public String getId_news() {
        return id_news;
    }

    /**
     *
     * @param id_news
     * The id_news
     */
    public void setId_news(String id_news) {
        this.id_news = id_news;
    }

    /**
     *
     * @return
     * The like
     */
    public boolean getLike() {
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
