package com.cinesearch.DAO;

import com.cinesearch.models.Ator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Classe responsável por fazer a conexão com o banco de dados em Ator
 * CRUD na entidade ator
 */
public class AtorDAO extends DAO {

    public AtorDAO() {
        super();
    }

    public boolean inserir(Ator ator) {
        boolean status = false;
        String sql = "INSERT INTO actors (name, profile_url, popularity, birthday, place_of_birth, deathday, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, ator.getNome());
            preparedStatement.setString(2, ator.getProfileUrl());
            preparedStatement.setBigDecimal(3, ator.getPopularidade());
            preparedStatement.setDate(4, new java.sql.Date(ator.getDataNascimento().getTime()));
            preparedStatement.setString(5, ator.getLocalNascimento());
            preparedStatement.setDate(6, ator.getDataMorte() != null ? new java.sql.Date(ator.getDataMorte().getTime()) : null);
            preparedStatement.setInt(7, ator.getGenero());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir ator", e);
        }
        return status;
    }

    public boolean removeByID(int atorId) {
        boolean status = false;
        String sql = "DELETE FROM actors WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, atorId);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover ator", e);
        }
        return status;
    }

    public boolean atualizar(int id, Ator ator) {
        boolean status = false;
        String sql = "UPDATE actors SET name = ?, profile_url = ?, popularity = ?, birthday = ?, place_of_birth = ?, deathday = ?, gender = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, ator.getNome());
            preparedStatement.setString(2, ator.getProfileUrl());
            preparedStatement.setBigDecimal(3, ator.getPopularidade());
            preparedStatement.setDate(4, new java.sql.Date(ator.getDataNascimento().getTime()));
            preparedStatement.setString(5, ator.getLocalNascimento());
            preparedStatement.setDate(6, ator.getDataMorte() != null ? new java.sql.Date(ator.getDataMorte().getTime()) : null);
            preparedStatement.setInt(7, ator.getGenero());
            preparedStatement.setInt(8, id);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar ator", e);
        }
        return status;
    }

    public Ator getById(int id) {
        Ator atorProcurado = null;
        String sql = "SELECT * FROM actors WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                atorProcurado = new Ator();
                atorProcurado.setId(resultSet.getLong("id"));  // Corrigido para usar getLong
                atorProcurado.setNome(resultSet.getString("name"));
                atorProcurado.setProfileUrl(resultSet.getString("profile_url"));
                atorProcurado.setPopularidade(resultSet.getBigDecimal("popularity"));
                atorProcurado.setDataNascimento(resultSet.getDate("birthday"));
                atorProcurado.setLocalNascimento(resultSet.getString("place_of_birth"));
                atorProcurado.setDataMorte(resultSet.getDate("deathday"));
                atorProcurado.setGenero(resultSet.getInt("gender"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar ator", e);
        }
        return atorProcurado;
    }

    public LinkedList<Ator> listarN(int n) {
        LinkedList<Ator> atores = new LinkedList<>();
        String sql = "SELECT * FROM actors LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ator ator = new Ator();
                ator.setId(resultSet.getLong("id"));  // Corrigido para usar getLong
                ator.setNome(resultSet.getString("name"));
                ator.setProfileUrl(resultSet.getString("profile_url"));
                ator.setPopularidade(resultSet.getBigDecimal("popularity"));
                ator.setDataNascimento(resultSet.getDate("birthday"));
                ator.setLocalNascimento(resultSet.getString("place_of_birth"));
                ator.setDataMorte(resultSet.getDate("deathday"));
                ator.setGenero(resultSet.getInt("gender"));
                atores.add(ator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar atores", e);
        }
        return atores;
    }
}
