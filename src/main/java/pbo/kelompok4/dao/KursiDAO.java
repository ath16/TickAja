package pbo.kelompok4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pbo.kelompok4.model.Kursi;
import pbo.kelompok4.util.KoneksiDatabase;

public class KursiDAO {

    public List<Kursi> getKursiByStudio(int studioId) {
        List<Kursi> listKursi = new ArrayList<>();
        String query = "SELECT * FROM kursi WHERE studio_id = ? ORDER BY nomor_baris, nomor_kursi";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Kursi kursi = new Kursi(
                    rs.getInt("kursi_id"),     // INI YANG PENTING (ID ASLI)
                    rs.getInt("studio_id"),
                    rs.getString("nomor_baris"),
                    rs.getInt("nomor_kursi")
                );
                listKursi.add(kursi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listKursi;
    }
}