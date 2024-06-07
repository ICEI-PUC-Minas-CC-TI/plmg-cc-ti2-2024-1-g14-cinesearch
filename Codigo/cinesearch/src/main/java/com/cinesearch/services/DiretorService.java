package com.cinesearch.services;

import com.cinesearch.DAO.DiretorDAO;
import com.cinesearch.models.Diretor;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class DiretorService {

    private DiretorDAO diretorDAO = new DiretorDAO();
    private Gson gson = new Gson();

    public String listarDiretores(Request req, Response res) {
        res.type("application/json");
        return gson.toJson(diretorDAO.listarN(Integer.MAX_VALUE));
    }

    public String obterDiretorPorId(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Diretor diretor = diretorDAO.getById(id);
        if (diretor != null) {
            return gson.toJson(diretor);
        } else {
            res.status(404);
            return gson.toJson("Diretor não encontrado");
        }
    }

    public String criarDiretor(Request req, Response res) {
        res.type("application/json");
        Diretor diretor = gson.fromJson(req.body(), Diretor.class);
        boolean status = diretorDAO.inserir(diretor);
        if (status) {
            res.status(201);
            return gson.toJson("Diretor criado com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao criar diretor");
        }
    }

    public String atualizarDiretor(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Diretor diretor = gson.fromJson(req.body(), Diretor.class);
        boolean status = diretorDAO.atualizar(id, diretor);
        if (status) {
            return gson.toJson("Diretor atualizado com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao atualizar diretor");
        }
    }

    public String removerDiretor(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        boolean status = diretorDAO.removeByID(id);
        if (status) {
            return gson.toJson("Diretor removido com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao remover diretor");
        }
    }
}
