package pbo.kelompok4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pbo.kelompok4.model.LokasiBioskop;
import pbo.kelompok4.util.KoneksiDatabase;
    
public class LokasiDAO {
    
    public void tambahLokasi(LokasiBioskop lokasi) {
        String sql = """
                INSERT INTO lokasi_bioskop (nama_lokasi, alamat)
                VALUES (?, ?)
            """;
    
            try (Connection conn = KoneksiDatabase.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
    
                ps.setString(1, lokasi.getNamaLokasi());
                ps.setString(2, lokasi.getAlamat());
                ps.executeUpdate();
    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        // AMBIL semua lokasi bioskop
        public ArrayList<LokasiBioskop> getAllLokasi() {
            ArrayList<LokasiBioskop> list = new ArrayList<>();
            String sql = "SELECT * FROM lokasi_bioskop";
    
            try (Connection conn = KoneksiDatabase.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
    
                while (rs.next()) {
                    LokasiBioskop lokasi = new LokasiBioskop(
                            rs.getInt("id_lokasi"),
                            rs.getString("nama_lokasi"),
                            rs.getString("alamat")
                    );
                    list.add(lokasi);
                }
    
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
    }
    




