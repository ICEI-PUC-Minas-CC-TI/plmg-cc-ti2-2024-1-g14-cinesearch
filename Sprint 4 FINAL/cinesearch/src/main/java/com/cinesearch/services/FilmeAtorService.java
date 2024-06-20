package com.cinesearch.services;

import com.cinesearch.DAO.FilmeAtorDAO;
import com.cinesearch.models.FilmeAtor;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class FilmeAtorService {

    private FilmeAtorDAO filmeAtorDAO = new FilmeAtorDAO();
    private Gson gson = new Gson();

    public String listarFilmeAtores(Request req, Response res) {
        res.type("application/json");
        return gson.toJson(filmeAtorDAO.listarN(Integer.MAX_VALUE));
    }

    public String obterFilmeAtorPorId(Request req, Response res) {
        res.type("application/json");
        long filmeId = Long.parseLong(req.params(":filmeId"));
        long atorId = Long.parseLong(req.params(":atorId"));
        FilmeAtor filmeAtor = filmeAtorDAO.getById(filmeId, atorId);
        if (filmeAtor != null) {
            return gson.toJson(filmeAtor);
        } else {
            res.status(404);
            return gson.toJson("Associação Filme-Ator não encontrada");
        }
    }

    public String criarFilmeAtor(Request req, Response res) {
        res.type("application/json");
        FilmeAtor filmeAtor = gson.fromJson(req.body(), FilmeAtor.class);
        boolean status = filmeAtorDAO.inserir(filmeAtor);
        if (status) {
            res.status(201);
            return gson.toJson("Associação Filme-Ator criada com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao criar associação Filme-Ator");
        }
    }

    public String atualizarFilmeAtor(Request req, Response res) {
        res.type("application/json");
        long filmeId = Long.parseLong(req.params(":filmeId"));
        long atorId = Long.parseLong(req.params(":atorId"));
        FilmeAtor filmeAtor = gson.fromJson(req.body(), FilmeAtor.class);
        boolean status = filmeAtorDAO.atualizar(filmeId, atorId, filmeAtor);
        if (status) {
            return gson.toJson("Associação Filme-Ator atualizada com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao atualizar associação Filme-Ator");
        }
    }

    public String removerFilmeAtor(Request req, Response res) {
        res.type("application/json");
        long filmeId = Long.parseLong(req.params(":filmeId"));
        long atorId = Long.parseLong(req.params(":atorId"));
        boolean status = filmeAtorDAO.removeByIDs(filmeId, atorId);
        if (status) {
            return gson.toJson("Associação Filme-Ator removida com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao remover associação Filme-Ator");
        }
    }
}
