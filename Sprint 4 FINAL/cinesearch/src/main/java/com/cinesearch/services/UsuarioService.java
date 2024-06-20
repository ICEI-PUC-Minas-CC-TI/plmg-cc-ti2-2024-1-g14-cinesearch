package com.cinesearch.services;

import com.cinesearch.DAO.UsuarioDAO;
import com.cinesearch.models.Usuario;
import com.cinesearch.services.HashUtil;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import java.util.Collections;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Gson gson = new Gson();

    public String listarUsuarios(Request req, Response res) {
        res.type("application/json");
        return gson.toJson(usuarioDAO.listarN(Integer.MAX_VALUE));
    }

    public String login(Request req, Response res) {
        res.type("application/json");
        String email = req.queryParams("email");
        String senha = req.queryParams("senha");

        if (email == null || senha == null) {
            res.status(400); // Bad Request
            return gson.toJson(Collections.singletonMap("error", "Email e senha são obrigatórios"));
        }
        try {
            String senhaHash = HashUtil.hashMD5(senha);
            int userId = usuarioDAO.verificarLogin(email, senhaHash);
            Usuario usuario = usuarioDAO.getById(userId);
            req.session(true).attribute("usuarioLogado", usuario.getNomeUsuario());
            res.status(200); // OK
            return gson.toJson(Collections.singletonMap("userId", userId));
        } catch (RuntimeException e) {
            res.status(401); // Unauthorized
            return gson.toJson(Collections.singletonMap("error", "Email ou senha incorretos"));
        }
    }

    public String obterUsuarioPorId(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Usuario usuario = usuarioDAO.getById(id);
        if (usuario != null) {
            return gson.toJson(usuario);
        } else {
            res.status(404);
            return gson.toJson("Usuário não encontrado");
        }
    }

    public String criarUsuario(Request req, Response res) {
        res.type("application/json");
        Usuario usuario = gson.fromJson(req.body(), Usuario.class);
        
        // Hashing the password before storing it
        String senhaHash = HashUtil.hashMD5(usuario.getSenha());
        usuario.setSenha(senhaHash);

        System.out.println("Recebido usuario: " + usuario.getNomeUsuario() + ", " + usuario.getEmail() + ", " + usuario.getSenha());

        boolean status = usuarioDAO.inserir(usuario);
        if (status) {
            res.status(201);
            return gson.toJson("Usuário criado com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao criar usuário");
        }
    }

    public String atualizarUsuario(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        Usuario usuario = gson.fromJson(req.body(), Usuario.class);
        
        // Hashing the password before updating it
        String senhaHash = HashUtil.hashMD5(usuario.getSenha());
        usuario.setSenha(senhaHash);

        boolean status = usuarioDAO.atualizar(id, usuario);
        if (status) {
            return gson.toJson("Usuário atualizado com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao atualizar usuário");
        }
    }

    public String removerUsuario(Request req, Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        boolean status = usuarioDAO.removeByID(id);
        if (status) {
            return gson.toJson("Usuário removido com sucesso");
        } else {
            res.status(500);
            return gson.toJson("Erro ao remover usuário");
        }
    }
}
