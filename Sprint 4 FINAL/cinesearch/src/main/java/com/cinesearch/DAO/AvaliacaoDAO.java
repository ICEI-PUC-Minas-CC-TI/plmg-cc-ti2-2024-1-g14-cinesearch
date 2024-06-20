package com.cinesearch.DAO;

import com.cinesearch.models.Avaliacao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

/**
 * Classe responsável por fazer a conexão com o banco de dados em Avaliacao
 * CRUD na entidade avaliacao
 */
public class AvaliacaoDAO extends DAO {

    public AvaliacaoDAO() {
        super();
    }

    public boolean inserir(Avaliacao avaliacao) {
        boolean status = false;
        String sql = "INSERT INTO reviews (movie_id, user_id, rating, comment) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, avaliacao.getFilmeId());
            preparedStatement.setInt(2, avaliacao.getUsuarioId());
            preparedStatement.setInt(3, avaliacao.getNota());
            preparedStatement.setString(4, avaliacao.getComentario());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir avaliacao", e);
        }
        return status;
    }

    public boolean removeByID(int avaliacaoId) {
        boolean status = false;
        String sql = "DELETE FROM reviews WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, avaliacaoId);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover avaliacao", e);
        }
        return status;
    }

    public boolean atualizar(int id, Avaliacao avaliacao) {
        boolean status = false;
        String sql = "UPDATE reviews SET movie_id = ?, user_id = ?, rating = ?, comment = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, avaliacao.getFilmeId());
            preparedStatement.setInt(2, avaliacao.getUsuarioId());
            preparedStatement.setInt(3, avaliacao.getNota());
            preparedStatement.setString(4, avaliacao.getComentario());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar avaliacao", e);
        }
        return status;
    }

    public Avaliacao getById(int id) {
        Avaliacao avaliacaoProcurada = null;
        String sql = "SELECT * FROM reviews WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                avaliacaoProcurada = new Avaliacao();
                avaliacaoProcurada.setId(resultSet.getLong("id"));  // Corrigido para usar getLong
                avaliacaoProcurada.setFilmeId(resultSet.getInt("movie_id"));
                avaliacaoProcurada.setUsuarioId(resultSet.getInt("user_id"));
                avaliacaoProcurada.setNota(resultSet.getInt("rating"));
                avaliacaoProcurada.setComentario(resultSet.getString("comment"));
                avaliacaoProcurada.setCreatedAt(resultSet.getTimestamp("created_at"));  // Adicionado campo created_at
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar avaliacao", e);
        }
        return avaliacaoProcurada;
    }

    public LinkedList<Avaliacao> getAllUser(int userId) {
        LinkedList<Avaliacao> avaliacoes = new LinkedList<>();
        String sql = "SELECT * FROM reviews WHERE user_id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(resultSet.getLong("id"));  // Corrigido para usar getLong
                avaliacao.setFilmeId(resultSet.getInt("movie_id"));
                avaliacao.setUsuarioId(resultSet.getInt("user_id"));
                avaliacao.setNota(resultSet.getInt("rating"));
                avaliacao.setComentario(resultSet.getString("comment"));
                avaliacao.setCreatedAt(resultSet.getTimestamp("created_at"));  // Adicionado campo created_at
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar avaliacoes", e);
        }
        return avaliacoes;
    }

    public LinkedList<Avaliacao> getFilmeAval(int filmeId) {
        LinkedList<Avaliacao> avaliacoes = new LinkedList<>();
        String sql = "SELECT * FROM reviews WHERE movie_id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, filmeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(resultSet.getLong("id"));  // Corrigido para usar getLong
                avaliacao.setFilmeId(resultSet.getInt("movie_id"));
                avaliacao.setUsuarioId(resultSet.getInt("user_id"));
                avaliacao.setNota(resultSet.getInt("rating"));
                avaliacao.setComentario(resultSet.getString("comment"));
                avaliacao.setCreatedAt(resultSet.getTimestamp("created_at"));  // Adicionado campo created_at
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar avaliacoes", e);
        }
        return avaliacoes;
    }

    public LinkedList<Avaliacao> listarN(int n) {
        LinkedList<Avaliacao> avaliacoes = new LinkedList<>();
        String sql = "SELECT * FROM reviews LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(resultSet.getLong("id"));  // Corrigido para usar getLong
                avaliacao.setFilmeId(resultSet.getInt("movie_id"));
                avaliacao.setUsuarioId(resultSet.getInt("user_id"));
                avaliacao.setNota(resultSet.getInt("rating"));
                avaliacao.setComentario(resultSet.getString("comment"));
                avaliacao.setCreatedAt(resultSet.getTimestamp("created_at"));  // Adicionado campo created_at
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar avaliacoes", e);
        }
        return avaliacoes;
    }
}
