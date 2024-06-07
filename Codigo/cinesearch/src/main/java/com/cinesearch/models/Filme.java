package com.cinesearch.models;

public class Filme {
    private Long id;
    private String titulo;
   // private Diretor diretor;
   // private Genero genero;
    private int diretor_id;
    private int genero_id;
    private Integer anoLancamento;
    private String sinopse;
    private byte[] poster; // Armazenar a imagem do pôster como um array de bytes

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getdiretor_id() {
        return diretor_id;
    }

    public void setdiretor_id(int diretor_id) {
        this.diretor_id = diretor_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public int getgenero_id() {
        return genero_id;
    }

    public void setgenero_id(int genero_id) {
        this.genero_id = genero_id;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }
}
