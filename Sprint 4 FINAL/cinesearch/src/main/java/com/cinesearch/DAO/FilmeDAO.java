package com.cinesearch.DAO;

import com.cinesearch.models.Filme;
import com.cinesearch.models.Genero;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe responsável por fazer a conexão com o banco de dados em Filme
 * CRUD na entidade filme
 */
public class FilmeDAO extends DAO {

    public FilmeDAO() {
        super();
    }

    public List<Filme> buscarFilmes(String query) {
        List<Filme> filmes = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE title LIKE ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Filme filme = new Filme();
                filme.setId(rs.getLong("id")); // Certifique-se que o método espera um Long
                filme.setTitulo(rs.getString("title"));
                filme.setAno(rs.getString("year"));
                filme.setDataLancamento(rs.getDate("released"));
                filme.setDuracao(rs.getString("runtime"));
                filme.setSinopse(rs.getString("plot"));
                filme.setIdioma(rs.getString("language"));
                filme.setPremios(rs.getString("awards"));
                filme.setPoster(rs.getString("poster"));
                filme.setNota(rs.getBigDecimal("rating"));
                filme.setPopularidade(rs.getBigDecimal("popularity"));
                filmes.add(filme);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar filmes", e);
        }
        return filmes;
    }

    public boolean inserir(Filme filme) {
        boolean status = false;
        String sql = "INSERT INTO movies (title, year, released, runtime, plot, language, awards, poster, rating, popularity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, filme.getTitulo());
            preparedStatement.setString(2, filme.getAno());
            preparedStatement.setDate(3, new java.sql.Date(filme.getDataLancamento().getTime()));
            preparedStatement.setString(4, filme.getDuracao());
            preparedStatement.setString(5, filme.getSinopse());
            preparedStatement.setString(6, filme.getIdioma());
            preparedStatement.setString(7, filme.getPremios());
            preparedStatement.setString(8, filme.getPoster());
            preparedStatement.setBigDecimal(9, filme.getNota());
            preparedStatement.setBigDecimal(10, filme.getPopularidade());
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir filme", e);
        }
        return status;
    }

    public boolean removeByID(int filmeId) {
        boolean status = false;
        String sql = "DELETE FROM movies WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, filmeId);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover filme", e);
        }
        return status;
    }

    public boolean atualizar(int id, Filme filme) {
        boolean status = false;
        String sql = "UPDATE movies SET title = ?, year = ?, released = ?, runtime = ?, plot = ?, language = ?, awards = ?, poster = ?, rating = ?, popularity = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, filme.getTitulo());
            preparedStatement.setString(2, filme.getAno());
            preparedStatement.setDate(3, new java.sql.Date(filme.getDataLancamento().getTime()));
            preparedStatement.setString(4, filme.getDuracao());
            preparedStatement.setString(5, filme.getSinopse());
            preparedStatement.setString(6, filme.getIdioma());
            preparedStatement.setString(7, filme.getPremios());
            preparedStatement.setString(8, filme.getPoster());
            preparedStatement.setBigDecimal(9, filme.getNota());
            preparedStatement.setBigDecimal(10, filme.getPopularidade());
            preparedStatement.setInt(11, id);
            preparedStatement.executeUpdate();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar filme", e);
        }
        return status;
    }

    public Filme getById(int id) {
        Filme filmeProcurado = null;
        String sql = "SELECT * FROM movies WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                filmeProcurado = new Filme();
                filmeProcurado.setId(resultSet.getLong("id"));
                filmeProcurado.setTitulo(resultSet.getString("title"));
                filmeProcurado.setAno(resultSet.getString("year"));
                filmeProcurado.setDataLancamento(resultSet.getDate("released"));
                filmeProcurado.setDuracao(resultSet.getString("runtime"));
                filmeProcurado.setSinopse(resultSet.getString("plot"));
                filmeProcurado.setIdioma(resultSet.getString("language"));
                filmeProcurado.setPremios(resultSet.getString("awards"));
                filmeProcurado.setPoster(resultSet.getString("poster"));
                filmeProcurado.setNota(resultSet.getBigDecimal("rating"));
                filmeProcurado.setPopularidade(resultSet.getBigDecimal("popularity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar filme", e);
        }
        return filmeProcurado;
    }

    public LinkedList<Filme> listarN(int n) {
        LinkedList<Filme> filmes = new LinkedList<>();
        String sql = "SELECT * FROM movies LIMIT ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Filme filme = new Filme();
                filme.setId(resultSet.getLong("id"));
                filme.setTitulo(resultSet.getString("title"));
                filme.setAno(resultSet.getString("year"));
                filme.setDataLancamento(resultSet.getDate("released"));
                filme.setDuracao(resultSet.getString("runtime"));
                filme.setSinopse(resultSet.getString("plot"));
                filme.setIdioma(resultSet.getString("language"));
                filme.setPremios(resultSet.getString("awards"));
                filme.setPoster(resultSet.getString("poster"));
                filme.setNota(resultSet.getBigDecimal("rating"));
                filme.setPopularidade(resultSet.getBigDecimal("popularity"));
                filmes.add(filme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar filmes", e);
        }
        return filmes;
    }

    public boolean updatePoster(int id, String poster) {
        boolean status = false;
        String sql = "UPDATE movies SET poster = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, poster);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            status = true;
            System.out.println("Poster de ID " + id + " atualizado com sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar poster", e);
        }
        return status;
    }

    public Genero getGenreByMovieID(int movieID) {
        String sql = "SELECT genres.id, genres.name FROM genres INNER JOIN movie_genres ON genres.id = movie_genres.genre_id WHERE movie_genres.movie_id = ?";
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setInt(1, movieID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Genero genero = new Genero();
                genero.setId(resultSet.getInt("id"));
                genero.setNome(resultSet.getString("name"));
                return genero;
            } else {
                System.out.println("Gênero não encontrado para o filme ID = " + movieID);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar gênero, ID = " + movieID);
            throw new RuntimeException("Erro ao buscar gênero", e);
        }
    }

}
