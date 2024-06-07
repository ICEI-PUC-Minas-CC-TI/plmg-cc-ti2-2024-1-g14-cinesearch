package com.cinesearch.services;

import com.cinesearch.DAO.FilmeDAO;
import com.cinesearch.models.Filme;
import com.cinesearch.models.Genero;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;

public class FilmeService {
    private FilmeDAO filmeDAO = new FilmeDAO();
    private Gson gson = new Gson();

    public String listarFilmes(Request req, Response res) {
        res.type("application/json");
        List<Filme> filmes = filmeDAO.listarN(Integer.MAX_VALUE);
        List<FilmeComGenero> filmesComGenero = filmes.stream().map(filme -> {
            FilmeComGenero filmeComGenero = new FilmeComGenero(filme);
            filmeComGenero.setGenero(filmeDAO.getGenreByMovieID(filme.getId().intValue()));
            return filmeComGenero;
        }).collect(Collectors.toList());
        return gson.toJson(filmesComGenero);
    }

    public String obterFilmePorId(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Filme filme = filmeDAO.getById(id);
        if (filme != null) {
            FilmeComGenero filmeComGenero = new FilmeComGenero(filme);
            filmeComGenero.setGenero(filmeDAO.getGenreByMovieID(id));
            return gson.toJson(filmeComGenero);
        } else {
            res.status(404);
            return gson.toJson("Filme não encontrado");
        }
    }

    public String criarFilme(Request req, Response res) {
        res.type("application/json");
        Filme filme = gson.fromJson(req.body(), Filme.class);
        boolean status = filmeDAO.inserir(filme);
        if (status) {
            res.status(201);
            return gson.toJson("Filme criado com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao criar filme");
        }
    }

    public String atualizarFilme(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Filme filme = gson.fromJson(req.body(), Filme.class);
        boolean status = filmeDAO.atualizar(id, filme);
        if (status) {
            return gson.toJson("Filme atualizado com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao atualizar filme");
        }
    }

    public String removerFilme(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        boolean status = filmeDAO.removeByID(id);
        if (status) {
            return gson.toJson("Filme removido com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao remover filme");
        }
    }

    public String getGenre(Request req, Response res) {
        res.type("application/json");
        try {
            int movieID = Integer.parseInt(req.queryParams("movieID"));
            Genero genero = filmeDAO.getGenreByMovieID(movieID);
            if (genero != null) {
                return gson.toJson(genero);
            } else {
                res.status(404);
                return gson.toJson("Gênero não encontrado para o filme ID = " + movieID);
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return gson.toJson("ID do filme inválido");
        }
    }

    private class FilmeComGenero extends Filme {
        private Genero genero;

        public FilmeComGenero(Filme filme) {
            this.setId(filme.getId());
            this.setTitulo(filme.getTitulo());
            this.setdiretor_id(filme.getdiretor_id());
            this.setgenero_id(filme.getgenero_id());
            this.setAnoLancamento(filme.getAnoLancamento());
            this.setSinopse(filme.getSinopse());
            this.setPoster(filme.getPoster());
        }

        public Genero getGenero() {
            return genero;
        }

        public void setGenero(Genero genero) {
            this.genero = genero;
        }
    }
}