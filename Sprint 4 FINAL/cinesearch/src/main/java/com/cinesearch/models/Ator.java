package com.cinesearch.models;

import java.math.BigDecimal;
import java.util.Date;

public class Ator {
    private Long id;
    private String nome;
    private String profileUrl;
    private BigDecimal popularidade;
    private Date dataNascimento;
    private String localNascimento;
    private Date dataMorte;
    private int genero;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public BigDecimal getPopularidade() {
        return popularidade;
    }

    public void setPopularidade(BigDecimal popularidade) {
        this.popularidade = popularidade;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getLocalNascimento() {
        return localNascimento;
    }

    public void setLocalNascimento(String localNascimento) {
        this.localNascimento = localNascimento;
    }

    public Date getDataMorte() {
        return dataMorte;
    }

    public void setDataMorte(Date dataMorte) {
        this.dataMorte = dataMorte;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Ator{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", popularidade=" + popularidade +
                ", dataNascimento=" + dataNascimento +
                ", localNascimento='" + localNascimento + '\'' +
                ", dataMorte=" + dataMorte +
                ", genero=" + genero +
                '}';
    }
}
