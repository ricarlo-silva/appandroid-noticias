package com.unip.aps.aps_noticias.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ricarlo on 12/11/2016.
 */

public class NoticiaModel implements Serializable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_person")
    @Expose
    private String id_person;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("descricao")
    @Expose
    private String descricao;
    @SerializedName("data_publicacao")
    @Expose
    private String data_publicacao;
    @SerializedName("ids_person_voto")
    @Expose
    private List<String> ids_person_voto;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * The titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     *
     * @param titulo
     * The titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     *
     * @return
     * The descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     *
     * @param descricao
     * The descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     *
     * @return
     * The data_publicacao
     */
    public String getData_publicacao() {
        return data_publicacao;
    }

    /**
     *
     * @param data_publicacao
     * The data_publicacao
     */
    public void setData_publicacao(String data_publicacao) {
        this.data_publicacao = data_publicacao;
    }

    /**
     *
     * @return
     * The ids_person_voto
     */
    public List<String> getIds_person_voto() {
        return ids_person_voto;
    }

    /**
     *
     * @param ids_person_voto
     * The ids_person_voto
     */
    public void setIds_person_voto(List<String> ids_person_voto) {
        this.ids_person_voto = ids_person_voto;
    }

}
