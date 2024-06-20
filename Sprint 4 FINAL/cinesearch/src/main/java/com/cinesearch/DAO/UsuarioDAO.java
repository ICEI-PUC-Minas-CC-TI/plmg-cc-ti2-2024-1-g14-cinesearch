package com.cinesearch.DAO;

import com.cinesearch.models.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class UsuarioDAO extends DAO {

    public UsuarioDAO() {
        super();
    }

    public boolean inserir(Usuario usuario) {
        boolean status = false;
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            System.out.println("NomeUsuario: " + usuario.getNomeUsuario());
            System.out.println("Senha: " + usuario.getSenha());
            System.out.println("Email: " + usuario.getEmail());

            preparedStatement.setString(1, usuario.getNomeUsuario());
            preparedStatement.setString(2, usuario.getEmail());
            preparedStatement.setString(3, usuario.getSenha());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir usuario", e);
        }
        return status;
    }

    public int verificarLogin(String email, String senhaHash) {
        String sql = "SELECT id FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senhaHash);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
        String sql = "DELETE FROM users WHERE id = ?";
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
        String sql = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getNomeUsuario());
            preparedStatement.setString(2, usuario.getEmail());
            preparedStatement.setString(3, usuario.getSenha());
            preparedStatement.setInt(4, id);
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
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                usuarioProcurado = new Usuario();
                usuarioProcurado.setId(resultSet.getLong("id"));
                usuarioProcurado.setNomeUsuario(resultSet.getString("username"));
                usuarioProcurado.setSenha(resultSet.getString("password"));
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
        String sql = "SELECT * FROM users LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getLong("id"));
                usuario.setNomeUsuario(resultSet.getString("username"));
                usuario.setSenha(resultSet.getString("password"));
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
