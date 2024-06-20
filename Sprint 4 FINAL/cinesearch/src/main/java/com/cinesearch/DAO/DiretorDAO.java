package com.cinesearch.DAO;

import com.cinesearch.models.Diretor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Classe responsável por fazer a conexão com o banco de dados em Diretor
 * CRUD na entidade diretor
 */
public class DiretorDAO extends DAO {

    public DiretorDAO() {
        super();
    }

    public boolean inserir(Diretor diretor) {
        boolean status = false;
        String sql = "INSERT INTO directors (name, profile_url, popularity, birthday, place_of_birth, deathday, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, diretor.getNome());
            preparedStatement.setString(2, diretor.getProfileUrl());
            preparedStatement.setBigDecimal(3, diretor.getPopularidade());
            preparedStatement.setDate(4, new java.sql.Date(diretor.getDataNascimento().getTime()));
            preparedStatement.setString(5, diretor.getLocalNascimento());
            preparedStatement.setDate(6, diretor.getDataMorte() != null ? new java.sql.Date(diretor.getDataMorte().getTime()) : null);
            preparedStatement.setInt(7, diretor.getGenero());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir diretor", e);
        }
        return status;
    }

    public boolean removeByID(int diretorId) {
        boolean status = false;
        String sql = "DELETE FROM directors WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, diretorId);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover diretor", e);
        }
        return status;
    }

    public boolean atualizar(int id, Diretor diretor) {
        boolean status = false;
        String sql = "UPDATE directors SET name = ?, profile_url = ?, popularity = ?, birthday = ?, place_of_birth = ?, deathday = ?, gender = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, diretor.getNome());
            preparedStatement.setString(2, diretor.getProfileUrl());
            preparedStatement.setBigDecimal(3, diretor.getPopularidade());
            preparedStatement.setDate(4, new java.sql.Date(diretor.getDataNascimento().getTime()));
            preparedStatement.setString(5, diretor.getLocalNascimento());
            preparedStatement.setDate(6, diretor.getDataMorte() != null ? new java.sql.Date(diretor.getDataMorte().getTime()) : null);
            preparedStatement.setInt(7, diretor.getGenero());
            preparedStatement.setInt(8, id);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar diretor", e);
        }
        return status;
    }

    public Diretor getById(int id) {
        Diretor diretorProcurado = null;
        String sql = "SELECT * FROM directors WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                diretorProcurado = new Diretor();
                diretorProcurado.setId(resultSet.getLong("id"));
                diretorProcurado.setNome(resultSet.getString("name"));
                diretorProcurado.setProfileUrl(resultSet.getString("profile_url"));
                diretorProcurado.setPopularidade(resultSet.getBigDecimal("popularity"));
                diretorProcurado.setDataNascimento(resultSet.getDate("birthday"));
                diretorProcurado.setLocalNascimento(resultSet.getString("place_of_birth"));
                diretorProcurado.setDataMorte(resultSet.getDate("deathday"));
                diretorProcurado.setGenero(resultSet.getInt("gender"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar diretor", e);
        }
        return diretorProcurado;
    }

    public LinkedList<Diretor> listarN(int n) {
        LinkedList<Diretor> diretores = new LinkedList<>();
        String sql = "SELECT * FROM directors LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Diretor diretor = new Diretor();
                diretor.setId(resultSet.getLong("id"));
                diretor.setNome(resultSet.getString("name"));
                diretor.setProfileUrl(resultSet.getString("profile_url"));
                diretor.setPopularidade(resultSet.getBigDecimal("popularity"));
                diretor.setDataNascimento(resultSet.getDate("birthday"));
                diretor.setLocalNascimento(resultSet.getString("place_of_birth"));
                diretor.setDataMorte(resultSet.getDate("deathday"));
                diretor.setGenero(resultSet.getInt("gender"));
                diretores.add(diretor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar diretores", e);
        }
        return diretores;
    }
}
