package pbo.kelompok4.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/db_tickaja";
    private static final String USER = ""; // Ganti dengan user MySQL
    private static final String PASSWORD = ""; // Ganti dengan password MySQL

    private static Connection koneksi;

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                // Daftarkan driver JDBC
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                // Buat koneksi
                koneksi = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi ke database berhasil.");
            } catch (SQLException e) {
                System.err.println("Koneksi ke database gagal: " + e.getMessage());
            }
        }
        return koneksi;
    }

    // (Opsional) Method untuk menutup koneksi jika diperlukan
    public static void tutupKoneksi() {
        if (koneksi != null) {
            try {
                koneksi.close();
                koneksi = null;
                System.out.println("Koneksi ditutup.");
            } catch (SQLException e) {
                System.err.println("Gagal menutup koneksi: " + e.getMessage());
            }
        }
    }
}
