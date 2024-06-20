package com.cinesearch.DAO;

import com.cinesearch.models.FilmeAtor;
import com.cinesearch.models.FilmeAtorId;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Classe responsável por fazer a conexão com o banco de dados em FilmeAtor
 * CRUD na entidade filme_ator
 */
public class FilmeAtorDAO extends DAO {

    public FilmeAtorDAO() {
        super();
    }

    public boolean inserir(FilmeAtor filmeAtor) {
        boolean status = false;
        String sql = "INSERT INTO movie_actors (movie_id, actor_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, filmeAtor.getId().getFilmeId());
            preparedStatement.setLong(2, filmeAtor.getId().getAtorId());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir filme_ator", e);
        }
        return status;
    }

    public boolean removeByIDs(long filmeId, long atorId) {
        boolean status = false;
        String sql = "DELETE FROM movie_actors WHERE movie_id = ? AND actor_id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, filmeId);
            preparedStatement.setLong(2, atorId);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover filme_ator", e);
        }
        return status;
    }

    public boolean atualizar(long filmeId, long atorId, FilmeAtor filmeAtor) {
        boolean status = false;
        String sql = "UPDATE movie_actors SET movie_id = ?, actor_id = ? WHERE movie_id = ? AND actor_id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, filmeAtor.getId().getFilmeId());
            preparedStatement.setLong(2, filmeAtor.getId().getAtorId());
            preparedStatement.setLong(3, filmeId);
            preparedStatement.setLong(4, atorId);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar filme_ator", e);
        }
        return status;
    }

    public FilmeAtor getById(long filmeId, long atorId) {
        FilmeAtor filmeAtorProcurado = null;
        String sql = "SELECT * FROM movie_actors WHERE movie_id = ? AND actor_id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, filmeId);
            preparedStatement.setLong(2, atorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                FilmeAtorId filmeAtorId = new FilmeAtorId(resultSet.getLong("movie_id"), resultSet.getLong("actor_id"));
                filmeAtorProcurado = new FilmeAtor(filmeAtorId);
                // Relacionamentos
                FilmeDAO filmeDAO = new FilmeDAO();
                AtorDAO atorDAO = new AtorDAO();
                filmeAtorProcurado.setFilme(filmeDAO.getById(resultSet.getInt("movie_id")));
                filmeAtorProcurado.setAtor(atorDAO.getById(resultSet.getInt("actor_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar filme_ator", e);
        }
        return filmeAtorProcurado;
    }

    public LinkedList<FilmeAtor> listarN(int n) {
        LinkedList<FilmeAtor> filmeAtores = new LinkedList<>();
        String sql = "SELECT * FROM movie_actors LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                FilmeAtorId filmeAtorId = new FilmeAtorId(resultSet.getLong("movie_id"), resultSet.getLong("actor_id"));
                FilmeAtor filmeAtor = new FilmeAtor(filmeAtorId);
                // Relacionamentos
                FilmeDAO filmeDAO = new FilmeDAO();
                AtorDAO atorDAO = new AtorDAO();
                filmeAtor.setFilme(filmeDAO.getById(resultSet.getInt("movie_id")));
                filmeAtor.setAtor(atorDAO.getById(resultSet.getInt("actor_id")));
                filmeAtores.add(filmeAtor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar filme_atores", e);
        }
        return filmeAtores;
    }
}
