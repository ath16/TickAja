package pbo.kelompok4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pbo.kelompok4.model.Film;
import pbo.kelompok4.model.JadwalTayang;
import pbo.kelompok4.model.Studio;
import pbo.kelompok4.util.KoneksiDatabase;

public class JadwalDAO {

    // 1. Method untuk mengambil daftar jadwal berdasarkan ID Film
    // (Dipakai di User Dashboard -> Film Detail)
    public List<JadwalTayang> getJadwalByFilmId(int filmId) {
        List<JadwalTayang> listJadwal = new ArrayList<>();
        
        // Query JOIN: Mengambil Jadwal + Nama Studio + Judul Film
        String query = "SELECT j.jadwal_id, j.waktu_tayang, j.harga_tiket, " +
                       "s.studio_id, s.nama_studio, " +
                       "f.film_id, f.judul, f.poster_url, f.durasi_menit, f.deskripsi " +
                       "FROM jadwal_tayang j " +
                       "JOIN studio s ON j.studio_id = s.studio_id " +
                       "JOIN film f ON j.film_id = f.film_id " +
                       "WHERE j.film_id = ? " +
                       "ORDER BY j.waktu_tayang ASC";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, filmId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("judul"),
                    rs.getString("deskripsi"),
                    rs.getInt("durasi_menit"),
                    rs.getString("poster_url")
                );

                Studio studio = new Studio();
                studio.setId(rs.getInt("studio_id"));
                studio.setNamaStudio(rs.getString("nama_studio"));

                JadwalTayang jadwal = new JadwalTayang(
                    rs.getInt("jadwal_id"),
                    film,
                    studio,
                    rs.getTimestamp("waktu_tayang"),
                    rs.getInt("harga_tiket")
                );

                listJadwal.add(jadwal);
            }
        } catch (SQLException e) {
            System.err.println("Error getJadwalByFilmId: " + e.getMessage());
        }
        return listJadwal;
    }

    // 2. Method untuk mengambil SEMUA jadwal (Dipakai di Admin Dashboard)
    public List<JadwalTayang> getAllJadwal() {
        List<JadwalTayang> listJadwal = new ArrayList<>();
        String query = "SELECT j.jadwal_id, j.waktu_tayang, j.harga_tiket, " +
                       "s.studio_id, s.nama_studio, " +
                       "f.film_id, f.judul, f.poster_url, f.durasi_menit, f.deskripsi " +
                       "FROM jadwal_tayang j " +
                       "JOIN studio s ON j.studio_id = s.studio_id " +
                       "JOIN film f ON j.film_id = f.film_id " +
                       "ORDER BY j.waktu_tayang DESC";

        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("judul"),
                    rs.getString("deskripsi"),
                    rs.getInt("durasi_menit"),
                    rs.getString("poster_url")
                );

                Studio studio = new Studio();
                studio.setId(rs.getInt("studio_id"));
                studio.setNamaStudio(rs.getString("nama_studio"));

                JadwalTayang jadwal = new JadwalTayang(
                    rs.getInt("jadwal_id"),
                    film,
                    studio,
                    rs.getTimestamp("waktu_tayang"),
                    rs.getInt("harga_tiket")
                );

                listJadwal.add(jadwal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listJadwal;
    }

    // 3. Method Tambah Jadwal (Fitur Baru dari Branch Byan - Diperbaiki)
    // Dipakai saat Admin klik "Terbitkan Jadwal"
    public void tambahJadwal(int filmId, int studioId, String waktu, int harga) {
        String sql = "INSERT INTO jadwal_tayang (film_id, studio_id, waktu_tayang, harga_tiket) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, filmId);
            ps.setInt(2, studioId);
            ps.setString(3, waktu); // Format string "YYYY-MM-DD HH:MM:SS" diterima MySQL
            ps.setInt(4, harga);
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Method Hapus Jadwal
    public void hapusJadwal(int id) {
        String sql = "DELETE FROM jadwal_tayang WHERE jadwal_id = ?";
        try (Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}