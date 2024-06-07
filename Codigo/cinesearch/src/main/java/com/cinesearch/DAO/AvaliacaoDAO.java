package com.cinesearch.DAO;

import com.cinesearch.models.Avaliacao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "INSERT INTO avaliacao (classificacao, comentario, usuario_id, filme_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, avaliacao.getNota());
            preparedStatement.setString(2, avaliacao.getComentario());
            preparedStatement.setInt(3, avaliacao.getusuario_id());
            preparedStatement.setInt(4, avaliacao.getfilme_id());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DEU RUM NO BD PORRA");
            System.out.println(avaliacao.getfilme_id());

            throw new RuntimeException("Erro ao inserir avaliacao", e);
        }
        return status;
    }




    public boolean removeByID(int avaliacaoId) {
        boolean status = false;
        String sql = "DELETE FROM avaliacao WHERE id = ?";
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
        String sql = "UPDATE avaliacao SET classificacao = ?, comentario = ?, usuario_id = ?, filme_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, avaliacao.getNota());
            preparedStatement.setString(2, avaliacao.getComentario());
            preparedStatement.setInt(3, avaliacao.getusuario_id());
            preparedStatement.setInt(4, avaliacao.getfilme_id());
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
        String sql = "SELECT * FROM avaliacao WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                avaliacaoProcurada = new Avaliacao();
                avaliacaoProcurada.setId(resultSet.getLong("id"));
                avaliacaoProcurada.setNota(resultSet.getInt("classificacao"));
                avaliacaoProcurada.setComentario(resultSet.getString("comentario"));
                // Relacionamentos com Usuario e Filme
                // Assumindo que você já tenha um método para buscar Usuario e Filme por ID
                avaliacaoProcurada.setusuario_id((resultSet.getInt("usuario_id")));
                avaliacaoProcurada.setfilme_id((resultSet.getInt("filme_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar avaliacao", e);
        }
        return avaliacaoProcurada;
    }

    public LinkedList<Avaliacao> getAllUser(int userid){
        LinkedList<Avaliacao> avaliacoes = new LinkedList<>();
        String sql = "SELECT * FROM avaliacao WHERE usuario_id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(resultSet.getLong("id"));
                avaliacao.setNota(resultSet.getInt("classificacao"));
                avaliacao.setComentario(resultSet.getString("comentario"));
                // Relacionamentos com Usuario e Filme
                avaliacao.setusuario_id((resultSet.getInt("usuario_id")));
                avaliacao.setfilme_id((resultSet.getInt("filme_id")));
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar avaliacoes", e);
        }
        return avaliacoes;
    }

    public LinkedList<Avaliacao> getFilmeAval(int filmeID){
        LinkedList<Avaliacao> avaliacoes = new LinkedList<>();
        String sql = "SELECT * FROM avaliacao WHERE filme_id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, filmeID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(resultSet.getLong("id"));
                avaliacao.setNota(resultSet.getInt("classificacao"));
                avaliacao.setComentario(resultSet.getString("comentario"));
                // Relacionamentos com Usuario e Filme

                avaliacao.setusuario_id((resultSet.getInt("usuario_id")));
                avaliacao.setfilme_id((resultSet.getInt("filme_id")));
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DEU RUM NO BD PORRA");
            throw new RuntimeException("Erro ao listar avaliacoes", e);
        }
        return avaliacoes;
    }

    public LinkedList<Avaliacao> listarN(int n) {
        LinkedList<Avaliacao> avaliacoes = new LinkedList<>();
        String sql = "SELECT * FROM avaliacao LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(resultSet.getLong("id"));
                avaliacao.setNota(resultSet.getInt("classificacao"));
                avaliacao.setComentario(resultSet.getString("comentario"));
                // Relacionamentos com Usuario e Filme
                
                avaliacao.setusuario_id((resultSet.getInt("usuario_id")));
                avaliacao.setfilme_id((resultSet.getInt("filme_id")));
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar avaliacoes", e);
        }
        return avaliacoes;
    }
}
