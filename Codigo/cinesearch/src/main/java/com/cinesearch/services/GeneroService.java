package com.cinesearch.services;

import com.cinesearch.DAO.GeneroDAO;
import com.cinesearch.models.Genero;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class GeneroService {

    private GeneroDAO generoDAO = new GeneroDAO();
    private Gson gson = new Gson();

    public String listarGeneros(Request req, Response res) {
        res.type("application/json");
        return gson.toJson(generoDAO.listarN(Integer.MAX_VALUE));
    }

    public String obterGeneroPorId(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Genero genero = generoDAO.getById(id);
        if (genero != null) {
            return gson.toJson(genero);
        } else {
            res.status(404);
            return gson.toJson("Gênero não encontrado");
        }
    }

    public String criarGenero(Request req, Response res) {
        res.type("application/json");
        Genero genero = gson.fromJson(req.body(), Genero.class);
        boolean status = generoDAO.inserir(genero);
        if (status) {
            res.status(201);
            return gson.toJson("Gênero criado com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao criar gênero");
        }
    }

    public String atualizarGenero(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Genero genero = gson.fromJson(req.body(), Genero.class);
        boolean status = generoDAO.atualizar(id, genero);
        if (status) {
            return gson.toJson("Gênero atualizado com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao atualizar gênero");
        }
    }

    public String removerGenero(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        boolean status = generoDAO.removeByID(id);
        if (status) {
            return gson.toJson("Gênero removido com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao remover gênero");
        }
    }
}
