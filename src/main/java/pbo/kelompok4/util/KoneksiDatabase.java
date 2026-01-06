package pbo.kelompok4.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/db_tickaja";
    private static final String USER = "root"; 
    private static final String PASSWORD = "sqlJRI690"; // ganti dengan password sql pribadi

    public static Connection getKoneksi() {
        try {
            // Register Driver MySQL
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            
            // Selalu buat koneksi baru
            return DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (SQLException e) {
            System.err.println("Koneksi ke database gagal: " + e.getMessage());
            return null;
        }
    }
}