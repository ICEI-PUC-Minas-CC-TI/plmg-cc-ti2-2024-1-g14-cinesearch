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

    public boolean inserir(Ator ator){
    boolean status = false;
    String sql ="INSERT INTO ator (nome,data_nascimento,local_nascimento,filmes_trabalhados) VALUES (?,?,?,?)";
    try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
        preparedStatement.setString(1, ator.getNome());
        preparedStatement.setDate(2, new java.sql.Date(ator.getDataNascimento().getTime()));
        preparedStatement.setString(3, ator.getLocalNascimento());
        preparedStatement.setString(4, ator.getFilmesTrabalhados());
        preparedStatement.executeUpdate();
        status = true;
    } catch (SQLException e){
        e.printStackTrace();
        throw new RuntimeException("Erro ao inserir jogo", e);
    }
        return status;
    }

    public boolean removeByID(int AtorId){
        boolean status = false;
        String sql = "DELETE FROM ator WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, AtorId);
            preparedStatement.executeUpdate();
            status = true;  
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover jogo", e);
        }
        return status;
    }

    public boolean updatedFilme(int id, Ator ator){
        boolean status = false;
        String sql = "UPDATE ator SET nome = ?, data_nascimento = ?, local_nascimento = ?, filmes_trabalhados = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, ator.getNome());
            preparedStatement.setDate(2, new java.sql.Date(ator.getDataNascimento().getTime()));
            preparedStatement.setString(3, ator.getLocalNascimento());
            preparedStatement.setString(4, ator.getFilmesTrabalhados());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar jogo", e);
        }
        return status;
    }

    public Ator getById(int id){
        Ator atorProcurado = null;
        String sql = "SELECT * FROM ator WHERE id = ?"; 
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                atorProcurado = new Ator();
                atorProcurado.setId(resultSet.getLong("id"));
                atorProcurado.setNome(resultSet.getString("nome"));
                atorProcurado.setDataNascimento(resultSet.getDate("data_nascimento"));
                atorProcurado.setLocalNascimento(resultSet.getString("local_nascimento"));
                atorProcurado.setFilmesTrabalhados(resultSet.getString("filmes_trabalhados"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar jogo", e);
        }
        return atorProcurado;
    }

    public LinkedList<Ator> listarN(int n){
        LinkedList<Ator> atores = new LinkedList<>();
        String sql = "SELECT * FROM ator LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ator ator = new Ator();
                ator.setId(resultSet.getLong("id"));
                ator.setNome(resultSet.getString("nome"));
                ator.setDataNascimento(resultSet.getDate("data_nascimento"));
                ator.setLocalNascimento(resultSet.getString("local_nascimento"));
                ator.setFilmesTrabalhados(resultSet.getString("filmes_trabalhados"));
                atores.add(ator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar jogos", e);
        }
        return atores;
    }


}
