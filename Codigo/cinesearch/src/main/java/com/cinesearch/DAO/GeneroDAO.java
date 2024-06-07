package com.cinesearch.DAO;

import com.cinesearch.models.Genero;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Classe responsável por fazer a conexão com o banco de dados em Genero
 * CRUD na entidade genero
 */
public class GeneroDAO extends DAO {

    public GeneroDAO() {
        super();
    }

    public boolean inserir(Genero genero) {
        boolean status = false;
        String sql = "INSERT INTO genero (nome) VALUES (?)";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, genero.getNome());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir genero", e);
        }
        return status;
    }

    public boolean removeByID(int generoId) {
        boolean status = false;
        String sql = "DELETE FROM genero WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, generoId);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover genero", e);
        }
        return status;
    }

    public boolean atualizar(int id, Genero genero) {
        boolean status = false;
        String sql = "UPDATE genero SET nome = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, genero.getNome());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar genero", e);
        }
        return status;
    }

    public Genero getById(int id) {
        Genero generoProcurado = null;
        String sql = "SELECT * FROM genero WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                generoProcurado = new Genero();
                generoProcurado.setId(resultSet.getInt("id"));
                generoProcurado.setNome(resultSet.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar genero", e);
        }
        return generoProcurado;
    }

    public LinkedList<Genero> listarN(int n) {
        LinkedList<Genero> generos = new LinkedList<>();
        String sql = "SELECT * FROM genero LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Genero genero = new Genero();
                genero.setId(resultSet.getInt("id"));
                genero.setNome(resultSet.getString("nome"));
                generos.add(genero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar generos", e);
        }
        return generos;
    }
}
