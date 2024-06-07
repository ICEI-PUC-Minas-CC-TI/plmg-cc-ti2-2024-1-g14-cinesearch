package com.cinesearch.models;


import java.util.Date;

// Criar modelos de classes para o projeto para representar os dados entre front e banco de dados
public class Ator {

    private Long id;

    private String nome;

    //@Column(name = "data_nascimento")
    //@Temporal(TemporalType.DATE)
    private Date dataNascimento;

   // @Column(name = "local_nascimento")
    private String localNascimento;

   // @Column(name = "filmes_trabalhados")
    private String filmesTrabalhados;

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

    public String getFilmesTrabalhados() {
        return filmesTrabalhados;
    }

    public void setFilmesTrabalhados(String filmesTrabalhados) {
        this.filmesTrabalhados = filmesTrabalhados;
    }
}
