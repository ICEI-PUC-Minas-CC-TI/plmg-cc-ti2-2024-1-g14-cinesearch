package com.cinesearch.models;

import java.math.BigDecimal;
import java.sql.Date;

public class Filme {
    private Long id;
    private String titulo;
    private String ano;
    private Date dataLancamento;
    private String duracao;
    private String sinopse;
    private String idioma;
    private String premios;
    private String poster;
    private BigDecimal nota;
    private BigDecimal popularidade;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getPremios() {
        return premios;
    }

    public void setPremios(String premios) {
        this.premios = premios;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public BigDecimal getPopularidade() {
        return popularidade;
    }

    public void setPopularidade(BigDecimal popularidade) {
        this.popularidade = popularidade;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", ano='" + ano + '\'' +
                ", dataLancamento=" + dataLancamento +
                ", duracao='" + duracao + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", idioma='" + idioma + '\'' +
                ", premios='" + premios + '\'' +
                ", poster='" + poster + '\'' +
                ", nota=" + nota +
                ", popularidade=" + popularidade +
                '}';
    }
}
