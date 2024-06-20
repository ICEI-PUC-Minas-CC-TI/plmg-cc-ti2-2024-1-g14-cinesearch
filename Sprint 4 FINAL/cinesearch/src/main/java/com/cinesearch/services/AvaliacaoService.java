package com.cinesearch.services;

import com.cinesearch.DAO.AvaliacaoDAO;
import com.cinesearch.models.Avaliacao;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class AvaliacaoService {

    private AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    private Gson gson = new Gson();

    public String criarAvaliacao(Request req, Response res) {
        System.out.println("\n\nCriando Aval\n");

        res.type("application/json");
        Avaliacao avaliacao = gson.fromJson(req.body(), Avaliacao.class);
        boolean status = avaliacaoDAO.inserir(avaliacao);
        if (status) {
            res.status(201);
            return gson.toJson("Avaliação criada com sucesso");
        } else {
            res.status(500);
            System.out.println("\n\n Deu Ruim na criação Aval\n");

            return gson.toJson("Erro ao criar avaliação");
        }
    }

    public String obterAvaliacaoPorId(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Avaliacao avaliacao = avaliacaoDAO.getById(id);
        if (avaliacao != null) {
            return gson.toJson(avaliacao);
        } else {
            res.status(404);
            return gson.toJson("Avaliação não encontrada");
        }
    }

    public String atualizarAvaliacao(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Avaliacao avaliacao = gson.fromJson(req.body(), Avaliacao.class);
        boolean status = avaliacaoDAO.atualizar(id, avaliacao);
        if (status) {
            return gson.toJson("Avaliação atualizada com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao atualizar avaliação");
        }
    }

    public String removerAvaliacao(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        boolean status = avaliacaoDAO.removeByID(id);
        if (status) {
            return gson.toJson("Avaliação removida com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao remover avaliação");
        }
    }

    public String getFilmeAval(Request req, Response res) {
        res.type("application/json");
        int filmeId = Integer.parseInt(req.params(":id"));
        System.out.println("ID do filme: " + filmeId + "\n\n\n\n");
        return gson.toJson(avaliacaoDAO.getFilmeAval(filmeId));
    }

    public String listarAvaliacoes(Request req, Response res) {
        res.type("application/json");
        int n = Integer.parseInt(req.queryParams("limit"));
        return gson.toJson(avaliacaoDAO.listarN(n));
    }
}
