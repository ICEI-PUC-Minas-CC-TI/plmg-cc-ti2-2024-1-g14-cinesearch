package com.cinesearch.DAO;

import com.cinesearch.models.Filme;
import com.cinesearch.models.Genero;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Classe responsável por fazer a conexão com o banco de dados em Filme
 * CRUD na entidade filme
 */
public class FilmeDAO extends DAO {

    public FilmeDAO() {
        super();
    }

    public boolean inserir(Filme filme) {
        boolean status = false;
        String sql = "INSERT INTO filme (titulo, diretor_id, ator_id, genero_id, ano_lancamento, sinopse, poster) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, filme.getTitulo());
            preparedStatement.setInt(2, filme.getdiretor_id());
            preparedStatement.setInt(4, filme.getgenero_id());
            preparedStatement.setInt(5, filme.getAnoLancamento());
            preparedStatement.setString(6, filme.getSinopse());
            preparedStatement.setBytes(7, filme.getPoster());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir filme", e);
        }
        return status;
    }

    public boolean removeByID(int filmeId) {
        boolean status = false;
        String sql = "DELETE FROM filme WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, filmeId);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover filme", e);
        }
        return status;
    }

    public boolean atualizar(int id, Filme filme) {
        boolean status = false;
        String sql = "UPDATE filme SET titulo = ?, diretor_id = ?, ator_id = ?, genero_id = ?, ano_lancamento = ?, sinopse = ?, poster = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, filme.getTitulo());
            preparedStatement.setInt(2, filme.getdiretor_id());
            preparedStatement.setInt(4, filme.getgenero_id());
            preparedStatement.setInt(5, filme.getAnoLancamento());
            preparedStatement.setString(6, filme.getSinopse());
            preparedStatement.setBytes(7, filme.getPoster());
            preparedStatement.setInt(8, id);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar filme", e);
        }
        return status;
    }

    public Filme getById(int id) {
        Filme filmeProcurado = null;
        String sql = "SELECT * FROM filme WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                filmeProcurado = new Filme();
                filmeProcurado.setId(resultSet.getLong("id"));
                filmeProcurado.setTitulo(resultSet.getString("titulo"));
                // Relacionamentos
                DiretorDAO diretorDAO = new DiretorDAO();
                AtorDAO atorDAO = new AtorDAO();
                GeneroDAO generoDAO = new GeneroDAO();
                filmeProcurado.setdiretor_id((resultSet.getInt("diretor_id")));
                filmeProcurado.setgenero_id((resultSet.getInt("genero_id")));
                filmeProcurado.setAnoLancamento(resultSet.getInt("ano_lancamento"));
                filmeProcurado.setSinopse(resultSet.getString("sinopse"));
                filmeProcurado.setPoster(resultSet.getBytes("poster"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar filme", e);
        }
        return filmeProcurado;
    }

    public LinkedList<Filme> listarN(int n) {
        LinkedList<Filme> filmes = new LinkedList<>();
        String sql = "SELECT * FROM filme LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("\n\n\n\n\n\n\n\n\n\n REQUISIÇÃO DE FILMES: \n\n\n\n\n\n\n\n\n");
            while (resultSet.next()) {
                Filme filme = new Filme();
                filme.setId(resultSet.getLong("id"));
                filme.setTitulo(resultSet.getString("titulo"));
                // Relacionamentos
                filme.setdiretor_id((resultSet.getInt("diretor_id")));
                filme.setgenero_id((resultSet.getInt("genero_id")));
                filme.setAnoLancamento(resultSet.getInt("ano_lancamento"));
                filme.setSinopse(resultSet.getString("sinopse"));
                filme.setPoster(resultSet.getBytes("poster"));
                filmes.add(filme);
                System.out.println("PRINT BONITO DO ID AQUI Ó: " + filme.getId());

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar filmes", e);
        }
        return filmes;
    }

    public boolean updatepost(int id, byte[] poster) {
        boolean status = false;
        String sql = "UPDATE filme SET poster = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setBytes(1, poster);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            status = true;
            System.out.println("Poster  de ID "+ id + "atualizado com sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar poster", e);
        }
        return status;
    }

    public Genero getGenreByMovieID(int movieID) {
        String sql = "SELECT genero.id, genero.nome FROM genero INNER JOIN filme ON genero.id = filme.genero_id WHERE filme.id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, movieID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Genero genero = new Genero();
                genero.setId( resultSet.getInt("id"));
                genero.setNome(resultSet.getString("nome"));
                return genero;
            } else {
                System.out.println("Gênero não encontrado para o filme ID = " + movieID);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar gênero, ID = " + movieID);
            throw new RuntimeException("Erro ao buscar gênero", e);
        }
    }
}
