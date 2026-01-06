package pbo.kelompok4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pbo.kelompok4.model.Studio;
import pbo.kelompok4.util.KoneksiDatabase;

public class LokasiDAO {

    // Method untuk mengambil semua data Studio (Untuk ComboBox di Admin)
    public List<Studio> getAllStudio() {
        List<Studio> listStudio = new ArrayList<>();
        String query = "SELECT * FROM studio";

        try (Connection conn = KoneksiDatabase.getKoneksi(); // Gunakan getKoneksi()
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Studio studio = new Studio(
                    rs.getInt("studio_id"),
                    rs.getInt("lokasi_id"),
                    rs.getString("nama_studio")
                );
                listStudio.add(studio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listStudio;
    }
}