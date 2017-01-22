package com.noticias_now.model;

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
    @SerializedName("person")
    @Expose
    private UsuarioModel person;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("descricao")
    @Expose
    private String descricao;
    @SerializedName("data_publicacao")
    @Expose
    private String data_publicacao;
    @SerializedName("votou")
    @Expose
    private boolean votou;
    @SerializedName("curtidas")
    @Expose
    private List<CurtidaModel> curtidas;

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
     * The person
     */
    public UsuarioModel getPerson() {
        return person;
    }

    /**
     *
     * @param person
     * The person
     */
    public void setPerson(UsuarioModel person) {
        this.person = person;
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
     * The tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     *
     * @param tipo
     * The tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
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
     * The votou
     */
    public boolean getVotou() {
        return votou;
    }

    /**
     *
     * @param votou
     * The votou
     */
    public void setVotou(boolean votou) {
        this.votou = votou;
    }

    /**
     *
     * @return
     * The curtidas
     */
    public List<CurtidaModel> getCurtidas() {
        return curtidas;
    }

    /**
     *
     * @param curtidas
     * The curtidas
     */
    public void setCurtidas(List<CurtidaModel> curtidas) {
        this.curtidas = curtidas;
    }

}
