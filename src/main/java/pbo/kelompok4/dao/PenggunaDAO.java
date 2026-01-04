package pbo.kelompok4.dao;

import pbo.kelompok4.model.Pengguna;
import pbo.kelompok4.util.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PenggunaDAO {

    public Pengguna login(String username, String password) {
        String query = "SELECT * FROM pengguna WHERE username = ? AND password = ?";
        try (Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password); // Catatan: Di dunia nyata harus di-hash, tapi untuk tugas ini plain text dulu.
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Pengguna(
                    rs.getInt("pengguna_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}