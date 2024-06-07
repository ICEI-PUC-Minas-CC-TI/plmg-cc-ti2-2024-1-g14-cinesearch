package com.cinesearch.DAO;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por fazer a conexão com o banco de dados
 */
public class DAO {

    protected Connection conexao;

    public DAO() {
        conectar();
    }

    public boolean conectar() {
        String driverName = "com.mysql.cj.jdbc.Driver";
        String serverName = "localhost";
        String mydatabase = "cinesearchdb";
        int porta = 3306; // Porta padrão do MySQL
        String url = "jdbc:mysql://" + serverName + ":" + porta + "/" + mydatabase + "?useUnicode=true&characterEncoding=UTF-8&characterSetResults=utf8mb4";
        String username = "root";
        String password = "cinesearch";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com o MySQL!\n\n");
            escreverArquivoConexao("Conexão efetuada com o MySQL!");
        } catch (ClassNotFoundException e) {
            System.err.println("Conexão NÃO efetuada com o MySQL -- Driver não encontrado -- " + e.getMessage());
            escreverArquivoConexao("Conexão NÃO efetuada com o MySQL -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o MySQL -- " + e.getMessage());
            escreverArquivoConexao("Conexão NÃO efetuada com o MySQL -- " + e.getMessage());
        }

        return status;
    }

    private void escreverArquivoConexao(String mensagem) {
        try (FileWriter fileWriter = new FileWriter("conexao_status.txt", true); 
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(mensagem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
