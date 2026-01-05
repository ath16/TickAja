package pbo.kelompok4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pbo.kelompok4.model.Admin;
import pbo.kelompok4.model.Pengguna;
import pbo.kelompok4.model.User;
import pbo.kelompok4.util.KoneksiDatabase;

public class PenggunaDAO {

    // Method untuk Mendaftarkan User Baru (Register)
    public boolean registerUser(User user) {
        // Query SQL untuk memasukkan data
        // Catatan: no_telepon belum ada di database, jadi kita simpan data intinya dulu
        String query = "INSERT INTO pengguna (username, password, nama_lengkap, email, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Mengisi tanda tanya (?) dengan data dari object User
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Idealnya password di-hash (enkripsi) dulu
            stmt.setString(3, user.getNamaLengkap());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, "user"); // Role otomatis diset sebagai 'user'

            // Jalankan perintah INSERT
            int rowsAffected = stmt.executeUpdate();
            
            // Jika baris yang berubah > 0, berarti berhasil
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Gagal Register: " + e.getMessage());
            return false;
        }
    }

    // Method untuk Cek Login (Akan dipakai User & Admin)
    public Pengguna login(String username, String password) {
        String query = "SELECT * FROM pengguna WHERE username = ? AND password = ?";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                
                // Polimorfisme: Mengembalikan object Admin atau User tergantung role
                if ("admin".equalsIgnoreCase(role)) {
                    Admin admin = new Admin();
                    admin.setId(rs.getInt("pengguna_id"));
                    admin.setUsername(rs.getString("username"));
                    admin.setPassword(rs.getString("password"));
                    admin.setNamaLengkap(rs.getString("nama_lengkap"));
                    admin.setEmail(rs.getString("email"));
                    return admin;

                } else {
                    User user = new User();
                    user.setId(rs.getInt("pengguna_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setNamaLengkap(rs.getString("nama_lengkap"));
                    user.setEmail(rs.getString("email"));
                    // user.setNoTelepon(...) -> Nanti jika kolom DB sudah ditambahkan
                    return user;
                }
            }

        } catch (SQLException e) {
            System.err.println("Gagal Login: " + e.getMessage());
        }
        
        return null; // Jika login gagal atau user tidak ditemukan
    }
    
    // Method cek apakah username sudah ada (Untuk validasi di Register)
    public boolean isUsernameExists(String username) {
        String query = "SELECT 1 FROM pengguna WHERE username = ?";
        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // True jika username ditemukan
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}