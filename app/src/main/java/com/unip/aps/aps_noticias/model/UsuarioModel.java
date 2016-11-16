package com.unip.aps.aps_noticias.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ricarlo on 10/11/2016.
 */

public class UsuarioModel implements Serializable {

    @SerializedName("IdPerfil")
    @Expose
    private int idPerfil;
    @SerializedName("NomePerfil")
    @Expose
    private String nomePerfil;
    @SerializedName("FotoPerfil")
    @Expose
    private String fotoPerfil;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("Senha")
    @Expose
    private String senha;

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
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The Token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     *
     * @param senha
     * The Senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

}
