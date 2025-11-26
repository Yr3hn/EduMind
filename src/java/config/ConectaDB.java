package config;

import java.sql.*;

public class ConectaDB {
    
    public static Connection conectar() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/edumindb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "");
        } catch(ClassNotFoundException ex){
            System.out.println("Erro - Driver JDBC: " + ex);
        } catch(SQLException ex){
            System.out.println("Erro - SQL: " + ex);
        }
        return conn;
    }
}