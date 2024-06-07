package com.cinesearch.models;




public class Avaliacao {

    private Long id;

    private Integer nota;

    private String comentario;

    private int filme_id;

    private int usuario_id;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getfilme_id() {
        return filme_id;
    }

    public void setfilme_id(int filme_id) {
        this.filme_id = filme_id;
    }

    public int getusuario_id() {
        return usuario_id;
    }

    public void setusuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}
