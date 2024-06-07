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
        String sql = "INSERT INTO diretor (nome, data_nascimento, local_nascimento, filmes_trabalhados) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, diretor.getNome());
            preparedStatement.setDate(2, new java.sql.Date(diretor.getDataNascimento().getTime()));
            preparedStatement.setString(3, diretor.getLocalNascimento());
            preparedStatement.setString(4, diretor.getFilmesTrabalhados());
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
        String sql = "DELETE FROM diretor WHERE id = ?";
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
        String sql = "UPDATE diretor SET nome = ?, data_nascimento = ?, local_nascimento = ?, filmes_trabalhados = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, diretor.getNome());
            preparedStatement.setDate(2, new java.sql.Date(diretor.getDataNascimento().getTime()));
            preparedStatement.setString(3, diretor.getLocalNascimento());
            preparedStatement.setString(4, diretor.getFilmesTrabalhados());
            preparedStatement.setInt(5, id);
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
        String sql = "SELECT * FROM diretor WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                diretorProcurado = new Diretor();
                diretorProcurado.setId(resultSet.getLong("id"));
                diretorProcurado.setNome(resultSet.getString("nome"));
                diretorProcurado.setDataNascimento(resultSet.getDate("data_nascimento"));
                diretorProcurado.setLocalNascimento(resultSet.getString("local_nascimento"));
                diretorProcurado.setFilmesTrabalhados(resultSet.getString("filmes_trabalhados"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar diretor", e);
        }
        return diretorProcurado;
    }

    public LinkedList<Diretor> listarN(int n) {
        LinkedList<Diretor> diretores = new LinkedList<>();
        String sql = "SELECT * FROM diretor LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Diretor diretor = new Diretor();
                diretor.setId(resultSet.getLong("id"));
                diretor.setNome(resultSet.getString("nome"));
                diretor.setDataNascimento(resultSet.getDate("data_nascimento"));
                diretor.setLocalNascimento(resultSet.getString("local_nascimento"));
                diretor.setFilmesTrabalhados(resultSet.getString("filmes_trabalhados"));
                diretores.add(diretor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar diretores", e);
        }
        return diretores;
    }
}
