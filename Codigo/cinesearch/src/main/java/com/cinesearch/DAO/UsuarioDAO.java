package com.cinesearch.DAO;

import com.cinesearch.models.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Classe responsável por fazer a conexão com o banco de dados em Usuario
 * CRUD na entidade usuario
 */
public class UsuarioDAO extends DAO {

    public UsuarioDAO() {
        super();
    }

    public boolean inserir(Usuario usuario) {
        boolean status = false;
        String sql = "INSERT INTO usuario (nome, nome_usuario, senha, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getNomeUsuario());
            preparedStatement.setString(3, usuario.getSenha());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir usuario", e);
        }
        return status;
    }

    public int verificarLogin(String email, String senha) {
        String sql = "SELECT id FROM Usuario WHERE email = ? AND senha = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();
           if(resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new RuntimeException("Usuário não encontrado");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao verificar login", e);
        }
    }

    public boolean removeByID(int usuarioId) {
        boolean status = false;
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, usuarioId);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover usuario", e);
        }
        return status;
    }

    public boolean atualizar(int id, Usuario usuario) {
        boolean status = false;
        String sql = "UPDATE usuario SET nome = ?, nome_usuario = ?, senha = ?, email = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getNomeUsuario());
            preparedStatement.setString(3, usuario.getSenha());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar usuario", e);
        }
        return status;
    }

    public Usuario getById(int id) {
        Usuario usuarioProcurado = null;
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                usuarioProcurado = new Usuario();
                usuarioProcurado.setId(resultSet.getLong("id"));
                usuarioProcurado.setNome(resultSet.getString("nome"));
                usuarioProcurado.setNomeUsuario(resultSet.getString("nome_usuario"));
                usuarioProcurado.setSenha(resultSet.getString("senha"));
                usuarioProcurado.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuario", e);
        }
        return usuarioProcurado;
    }

    public LinkedList<Usuario> listarN(int n) {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        String sql = "SELECT * FROM usuario LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getLong("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setNomeUsuario(resultSet.getString("nome_usuario"));
                usuario.setSenha(resultSet.getString("senha"));
                usuario.setEmail(resultSet.getString("email"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar usuarios", e);
        }
        return usuarios;
    }
}
