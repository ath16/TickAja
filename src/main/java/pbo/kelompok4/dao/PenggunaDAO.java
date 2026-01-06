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
        String query = "INSERT INTO pengguna (username, password, nama_lengkap, email, no_telepon, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Idealnya password di-hash
            stmt.setString(3, user.getNamaLengkap());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getNoTelepon());
            stmt.setString(6, "user"); // Role otomatis 'user'

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Gagal Register: " + e.getMessage());
            return false;
        }
    }

    // Method untuk Cek Login (Mendukung Admin & User)
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
                    user.setNoTelepon(rs.getString("no_telepon"));
                    return user;
                }
            }

        } catch (SQLException e) {
            System.err.println("Gagal Login: " + e.getMessage());
        }
        
        return null; // Login gagal
    }
    
    // Method Validasi Username
    public boolean isUsernameExists(String username) {
        String query = "SELECT 1 FROM pengguna WHERE username = ?";
        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public java.util.List<User> getAllUsers() {
        java.util.List<User> users = new java.util.ArrayList<>();
        String query = "SELECT * FROM pengguna WHERE role = 'user'"; 
        try (Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                User u = new User();
                    u.setId(rs.getInt("pengguna_id"));
                    u.setUsername(rs.getString("username"));
                    u.setNamaLengkap(rs.getString("nama_lengkap"));
                    u.setEmail(rs.getString("email"));
                    u.setNoTelepon(rs.getString("no_telepon"));
                    u.setPassword(rs.getString("password")); // Hati-hati menampilkan password
                    users.add(u);
                }
            } catch (SQLException e) { e.printStackTrace(); }
        return users;
    }

    public void updateUser(User u) {
        String sql = "UPDATE pengguna SET nama_lengkap=?, email=?, no_telepon=?, password=? WHERE pengguna_id=?";
        try (Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, u.getNamaLengkap());
                ps.setString(2, u.getEmail());
                ps.setString(3, u.getNoTelepon());
                ps.setString(4, u.getPassword());
                ps.setInt(5, u.getId());
                ps.executeUpdate();
            } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM pengguna WHERE pengguna_id=?";
        try (Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException e) { e.printStackTrace(); }
    }
}