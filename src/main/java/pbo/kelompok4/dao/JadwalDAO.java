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

    // Method untuk mengambil daftar jadwal berdasarkan ID Film
    // Dipakai di halaman FilmDetail (User)
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
                // 1. Buat Objek Film dari hasil query
                // Kita gunakan constructor lengkap sesuai Film.java
                Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("judul"),
                    rs.getString("deskripsi"),
                    rs.getInt("durasi_menit"),
                    rs.getString("poster_url")
                );

                // 2. Buat Objek Studio
                Studio studio = new Studio();
                studio.setId(rs.getInt("studio_id"));
                studio.setNamaStudio(rs.getString("nama_studio"));
                // studio.setLokasiId(...) jika perlu

                // 3. Buat Objek JadwalTayang (Gabungan semuanya)
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

    // Method untuk mengambil SEMUA jadwal (Dipakai di Admin Dashboard)
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
}