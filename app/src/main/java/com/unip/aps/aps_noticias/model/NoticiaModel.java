package com.unip.aps.aps_noticias.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ricarlo on 12/11/2016.
 */

public class NoticiaModel implements Serializable{

    @SerializedName("IdPerfil")
    @Expose
    private int idPerfil;
    @SerializedName("NomePerfil")
    @Expose
    private String nomePerfil;
    @SerializedName("FotoPerfil")
    @Expose
    private String fotoPerfil;
    @SerializedName("IdPublicacao")
    @Expose
    private int idPublicacao;
    @SerializedName("TituloPublicacao")
    @Expose
    private String tituloPublicacao;
    @SerializedName("TextoPublicacao")
    @Expose
    private String textoPublicacao;
    @SerializedName("DataHora")
    @Expose
    private String dataHora;
    @SerializedName("QuantidadeVotos")
    @Expose
    private int quantidadeVotos;
    @SerializedName("Voto")
    @Expose
    private boolean voto;

    /**
     *
     * @return
     * The idPerfil
     */
    public int getIdPerfil() {
        return idPerfil;
    }

    /**
     *
     * @param idPerfil
     * The IdPerfil
     */
    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    /**
     *
     * @return
     * The nomePerfil
     */
    public String getNomePerfil() {
        return nomePerfil;
    }

    /**
     *
     * @param nomePerfil
     * The NomePerfil
     */
    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    /**
     *
     * @return
     * The fotoPerfil
     */
    public String getFotoPerfil() {
        return fotoPerfil;
    }

    /**
     *
     * @param fotoPerfil
     * The FotoPerfil
     */
    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    /**
     *
     * @return
     * The idPublicacao
     */
    public int getIdPublicacao() {
        return idPublicacao;
    }

    /**
     *
     * @param idPublicacao
     * The IdPublicacao
     */
    public void setIdPublicacao(int idPublicacao) {
        this.idPublicacao = idPublicacao;
    }

    /**
     *
     * @return
     * The tituloPublicacao
     */
    public String getTituloPublicacao() {
        return tituloPublicacao;
    }

    /**
     *
     * @param tituloPublicacao
     * The TituloPublicacao
     */
    public void setTituloPublicacao(String tituloPublicacao) {
        this.tituloPublicacao = tituloPublicacao;
    }

    /**
     *
     * @return
     * The textoPublicacao
     */
    public String getTextoPublicacao() {
        return textoPublicacao;
    }

    /**
     *
     * @param textoPublicacao
     * The TextoPublicacao
     */
    public void setTextoPublicacao(String textoPublicacao) {
        this.textoPublicacao = textoPublicacao;
    }

    /**
     *
     * @return
     * The dataHora
     */
    public String getDataHora() {
        return dataHora;
    }

    /**
     *
     * @param dataHora
     * The DataHora
     */
    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    /**
     *
     * @return
     * The quantidadeVotos
     */
    public int getQuantidadeVotos() {
        return quantidadeVotos;
    }

    /**
     *
     * @param quantidadeVotos
     * The QuantidadeVotos
     */
    public void setQuantidadeVotos(int quantidadeVotos) {
        this.quantidadeVotos = quantidadeVotos;
    }

    /**
     *
     * @return
     * The voto
     */
    public boolean getVoto() {
        return voto;
    }

    /**
     *
     * @param voto
     * The QuantidadeComentarios
     */
    public void setVoto(boolean voto) {
        this.voto = voto;
    }

}
