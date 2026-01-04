package pbo.kelompok4.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/db_tickaja";

    private static final String USER = "root"; // user MySQL
    private static final String PASSWORD = ""; // Password MySQL

    public static Connection getKoneksi() {
        // Hapus pengecekan "if (koneksi == null)"
        // Agar selalu return koneksi baru (belum closed)
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            
            // Selalu buat koneksi baru
            Connection koneksiBaru = DriverManager.getConnection(URL, USER, PASSWORD);
            return koneksiBaru;
            
        } catch (SQLException e) {
            System.err.println("Koneksi ke database gagal: " + e.getMessage());
            return null;
        }
    }
}