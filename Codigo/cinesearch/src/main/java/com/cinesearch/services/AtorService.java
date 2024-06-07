package com.cinesearch.services;

import com.cinesearch.DAO.AtorDAO;
import com.cinesearch.models.Ator;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class AtorService {

    private AtorDAO atorDAO = new AtorDAO();
    private Gson gson = new Gson();

    public String listarAtores(Request req, Response res) {
        res.type("application/json");
        return gson.toJson(atorDAO.listarN(Integer.MAX_VALUE));
    }

    public String obterAtorPorId(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Ator ator = atorDAO.getById(id);
        if (ator != null) {
            return gson.toJson(ator);
        } else {
            res.status(404);
            return gson.toJson("Ator não encontrado");
        }
    }

    public String criarAtor(Request req, Response res) {
        res.type("application/json");
        Ator ator = gson.fromJson(req.body(), Ator.class);
        boolean status = atorDAO.inserir(ator);
        if (status) {
            res.status(201);
            return gson.toJson("Ator criado com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao criar ator");
        }
    }

    public String atualizarAtor(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Ator ator = gson.fromJson(req.body(), Ator.class);
        boolean status = atorDAO.updatedFilme(id, ator);
        if (status) {
            return gson.toJson("Ator atualizado com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao atualizar ator");
        }
    }

    public String removerAtor(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        boolean status = atorDAO.removeByID(id);
        if (status) {
            return gson.toJson("Ator removido com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao remover ator");
        }
    }
}
