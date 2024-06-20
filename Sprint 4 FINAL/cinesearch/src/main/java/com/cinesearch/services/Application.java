package com.cinesearch.services;

import static spark.Spark.*;
import com.cinesearch.services.AvaliacaoService;
import com.cinesearch.services.AtorService;
import com.cinesearch.services.DiretorService;
import com.cinesearch.services.FilmeService;
import com.cinesearch.services.UsuarioService;
import com.cinesearch.services.GeneroService;
import com.cinesearch.services.FilmeAtorService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
// Definindo a conexão com o MySQL local
private static Connection conexao;

public static void main(String[] args) {
    // Configura a porta na qual o servidor irá rodar
    port(9091);

    // Inicializa a conexão com o MySQL local
    iniciarConexao();

    // Serviços disponíveis
    AvaliacaoService avaliacaoService = new AvaliacaoService();
    AtorService atorService = new AtorService();
    DiretorService diretorService = new DiretorService();
    FilmeService filmeService = new FilmeService();
    UsuarioService usuarioService = new UsuarioService();
    GeneroService generoService = new GeneroService();
    FilmeAtorService filmeAtorService = new FilmeAtorService();

    // Habilita o suporte a CORS (Cross-Origin Resource Sharing)
    options("/*", (request, response) -> {
        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
        if (accessControlRequestHeaders != null) {
            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
        }

        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
        if (accessControlRequestMethod != null) {
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
        }

        return "OK";
    });

    before((request, response) -> {
        response.header("Access-Control-Allow-Origin", "*"); // Permitir todas as origens (modifique conforme necessário)
    });

    // Rotas para Avaliação
    path("/avaliacoes", () -> {
        post("", (req, res) -> avaliacaoService.criarAvaliacao(req, res));
        get("/:id", (req, res) -> avaliacaoService.obterAvaliacaoPorId(req, res));
        put("/:id", (req, res) -> avaliacaoService.atualizarAvaliacao(req, res));
        delete("/:id", (req, res) -> avaliacaoService.removerAvaliacao(req, res));
        get("", (req, res) -> avaliacaoService.listarAvaliacoes(req, res));
        get("/filmeAval/:id", (req, res) -> avaliacaoService.getFilmeAval(req, res));
    });

    path("/Hello", () -> {
        System.out.println("\n\nHello World\n\n");
        get("", (req, res) -> "Hello World");
    });

    // Rotas para Ator
    path("/atores", () -> {
        get("", (req, res) -> atorService.listarAtores(req, res));
        get("/:id", (req, res) -> atorService.obterAtorPorId(req, res));
        post("", (req, res) -> atorService.criarAtor(req, res));
        put("/:id", (req, res) -> atorService.atualizarAtor(req, res));
        delete("/:id", (req, res) -> atorService.removerAtor(req, res));
    });

    // Rotas para Diretor
    path("/diretores", () -> {
        get("", (req, res) -> diretorService.listarDiretores(req, res));
        get("/:id", (req, res) -> diretorService.obterDiretorPorId(req, res));
        post("", (req, res) -> diretorService.criarDiretor(req, res));
        put("/:id", (req, res) -> diretorService.atualizarDiretor(req, res));
        delete("/:id", (req, res) -> diretorService.removerDiretor(req, res));
    });

    // Rotas para Filme
    path("/filmes", () -> {
        get("", (req, res) -> filmeService.listarFilmes(req, res));
        get("/:id", (req, res) -> filmeService.obterFilmePorId(req, res));
        post("", (req, res) -> filmeService.criarFilme(req, res));
        put("/:id", (req, res) -> filmeService.atualizarFilme(req, res));
        delete("/:id", (req, res) -> filmeService.removerFilme(req, res));
        get("/genero", (req, res) -> filmeService.getGenre(req, res));
        get("/search", (req, res) -> filmeService.buscarFilmes(req, res)); // Rota corrigida para busca de filmes
    });

    // Rotas para Usuario
    path("/usuarios", () -> {
        get("", (req, res) -> usuarioService.listarUsuarios(req, res));
        get("/:id", (req, res) -> usuarioService.obterUsuarioPorId(req, res));
        post("", (req, res) -> usuarioService.criarUsuario(req, res));
        put("/:id", (req, res) -> usuarioService.atualizarUsuario(req, res));
        delete("/:id", (req, res) -> usuarioService.removerUsuario(req, res));
        post("/login", (req, res) -> usuarioService.login(req, res));
    });

    // Rotas para Genero
    path("/generos", () -> {
        get("", (req, res) -> generoService.listarGeneros(req, res));
        get("/:id", (req, res) -> generoService.obterGeneroPorId(req, res));
        post("", (req, res) -> generoService.criarGenero(req, res));
        put("/:id", (req, res) -> generoService.atualizarGenero(req, res));
        delete("/:id", (req, res) -> generoService.removerGenero(req, res));
    });

    // Rotas para FilmeAtor
    path("/filmeatores", () -> {
        get("", (req, res) -> filmeAtorService.listarFilmeAtores(req, res));
        get("/:filmeId/:atorId", (req, res) -> filmeAtorService.obterFilmeAtorPorId(req, res));
        post("", (req, res) -> filmeAtorService.criarFilmeAtor(req, res));
        put("/:filmeId/:atorId", (req, res) -> filmeAtorService.atualizarFilmeAtor(req, res));
        delete("/:filmeId/:atorId", (req, res) -> filmeAtorService.removerFilmeAtor(req, res));
    });

    // Encerra a conexão com o MySQL local ao finalizar o aplicativo
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        encerrarConexao();
    }));
}

// Método para iniciar a conexão com o MySQL local
private static void iniciarConexao() {
    String driverName = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/cinesearchdb";
    String username = "root";
    String password = "cinesearch";

    try {
        Class.forName(driverName);
        conexao = DriverManager.getConnection(url, username, password);
        System.out.println("Conexão efetuada com o MySQL local!");
    } catch (ClassNotFoundException e) {
        System.err.println("Conexão NÃO efetuada com o MySQL local -- Driver não encontrado -- " + e.getMessage());
    } catch (SQLException e) {
        System.err.println("Conexão NÃO efetuada com o MySQL local -- " + e.getMessage());
    }
}

// Método para encerrar a conexão com o MySQL local
private static void encerrarConexao() {
    if (conexao != null) {
        try {
            conexao.close();
            System.out.println("Conexão fechada com o MySQL local.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}
