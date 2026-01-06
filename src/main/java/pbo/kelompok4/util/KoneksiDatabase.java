package pbo.kelompok4.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/db_tickaja";
    private static final String USER = "root"; 
    
    // PERBAIKAN: Masukkan password database kamu di sini!
    // Jika password XAMPP kamu memang kosong, biarkan "". 
    // Tapi error "Access Denied" biasanya karena database BUTUH password.
    private static final String PASSWORD = "sqlJRI690"; 

    public static Connection getKoneksi() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            // Selalu buat koneksi baru (Non-Singleton) untuk menghindari closed connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Koneksi ke database gagal: " + e.getMessage());
            return null;
        }
    }
}