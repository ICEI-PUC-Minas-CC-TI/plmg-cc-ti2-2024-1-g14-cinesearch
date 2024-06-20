package com.cinesearch.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    protected Connection conexao;

    public DAO() {
        conectar();
    }

    public boolean conectar() {
        String driverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/cinesearchdb";
        String username = "root";
        String password = "cinesearch";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com o MySQL local!");
        } catch (ClassNotFoundException e) {
            System.err.println("Conexão NÃO efetuada com o MySQL local -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o MySQL local -- " + e.getMessage());
        }

        return status;
    }

    public void closeConnection() {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão fechada com o MySQL local.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
