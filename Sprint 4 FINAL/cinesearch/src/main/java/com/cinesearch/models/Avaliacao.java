package com.cinesearch.models;

import java.sql.Timestamp;

public class Avaliacao {

    private Long id;
    private int filme_id;
    private int usuario_id;
    private Integer nota;
    private String comentario;
    private Timestamp createdAt;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFilmeId() {
        return filme_id;
    }

    public void setFilmeId(int filme_id) {
        this.filme_id = filme_id;
    }

    public int getUsuarioId() {
        return usuario_id;
    }

    public void setUsuarioId(int usuario_id) {
        this.usuario_id = usuario_id;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "id=" + id +
                ", filme_id=" + filme_id +
                ", usuario_id=" + usuario_id +
                ", nota=" + nota +
                ", comentario='" + comentario + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
