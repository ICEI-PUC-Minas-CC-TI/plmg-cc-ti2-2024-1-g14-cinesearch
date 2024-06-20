package com.cinesearch.models;

import java.io.Serializable;

public class FilmeAtor implements Serializable {
    private FilmeAtorId id;
    private Filme filme;
    private Ator ator;

    // Construtor padrão
    public FilmeAtor() {}

    // Construtor com parâmetros
    public FilmeAtor(FilmeAtorId id) {
        this.id = id;
    }

    // Getters e Setters
    public FilmeAtorId getId() {
        return id;
    }

    public void setId(FilmeAtorId id) {
        this.id = id;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Ator getAtor() {
        return ator;
    }

    public void setAtor(Ator ator) {
        this.ator = ator;
    }

    @Override
    public String toString() {
        return "FilmeAtor{" +
                "id=" + id +
                ", filme=" + filme +
                ", ator=" + ator +
                '}';
    }
}
