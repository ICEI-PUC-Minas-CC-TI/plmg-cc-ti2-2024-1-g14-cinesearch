package com.cinesearch.models;




public class FilmeAtor {

    
    private Long filmeId;
    private Long atorId;


    private Filme filme;
    private Ator ator;

    // Getters e Setters
    public Long getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(Long filmeId) {
        this.filmeId = filmeId;
    }

    public Long getAtorId() {
        return atorId;
    }

    public void setAtorId(Long atorId) {
        this.atorId = atorId;
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
}
