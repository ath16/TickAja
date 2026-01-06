package pbo.kelompok4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pbo.kelompok4.model.Film;
import pbo.kelompok4.model.JadwalTayang;
import pbo.kelompok4.model.Kursi;
import pbo.kelompok4.model.Reservasi;
import pbo.kelompok4.model.Studio;
import pbo.kelompok4.util.KoneksiDatabase;

public class ReservasiDAO {

    // 1. Method Simpan Reservasi Baru (Dipakai BookingController)
    public boolean createReservasi(Reservasi reservasi, List<Kursi> listKursi) {
        String sqlReservasi = "INSERT INTO reservasi (pengguna_id, jadwal_id, waktu_reservasi, total_harga, status_pembayaran) VALUES (?, ?, ?, ?, ?)";
        String sqlDetail = "INSERT INTO detail_reservasi (reservasi_id, kursi_id, harga_kursi) VALUES (?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmtReservasi = null;
        PreparedStatement stmtDetail = null;

        try {
            conn = KoneksiDatabase.getKoneksi();
            conn.setAutoCommit(false); // Mulai Transaksi (Agar aman jika error di tengah)

            // A. Insert Header Reservasi
            stmtReservasi = conn.prepareStatement(sqlReservasi, Statement.RETURN_GENERATED_KEYS);
            stmtReservasi.setInt(1, reservasi.getPengguna().getId());
            stmtReservasi.setInt(2, reservasi.getJadwal().getId());
            stmtReservasi.setTimestamp(3, reservasi.getWaktuReservasi());
            stmtReservasi.setInt(4, reservasi.getTotalHarga());
            stmtReservasi.setString(5, reservasi.getStatusPembayaran());
            
            int affectedRows = stmtReservasi.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Gagal simpan reservasi.");

            // Ambil ID Reservasi yang baru saja dibuat
            int reservasiId = 0;
            try (ResultSet generatedKeys = stmtReservasi.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservasiId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Gagal ambil ID reservasi.");
                }
            }

            // B. Insert Detail Kursi (Batch Insert)
            stmtDetail = conn.prepareStatement(sqlDetail);
            for (Kursi kursi : listKursi) {
                stmtDetail.setInt(1, reservasiId);
                stmtDetail.setInt(2, kursi.getId()); 
                stmtDetail.setInt(3, reservasi.getJadwal().getHargaTiket()); 
                stmtDetail.addBatch();
            }
            stmtDetail.executeBatch();

            conn.commit(); // Komit Transaksi (Simpan Permanen)
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } // Batalkan jika error
            }
            return false;
        } finally {
            // Tutup semua resource manual
            try { if (stmtDetail != null) stmtDetail.close(); } catch (SQLException e) {}
            try { if (stmtReservasi != null) stmtReservasi.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // 2. Method Ambil History User (Dipakai OrderHistoryController) -- INI YANG HILANG SEBELUMNYA
    public List<Reservasi> getReservasiByUser(int userId) {
        List<Reservasi> list = new ArrayList<>();
        
        // Query JOIN Lengkap: Reservasi -> Jadwal -> Film & Studio
        String query = "SELECT r.reservasi_id, r.waktu_reservasi, r.total_harga, r.status_pembayaran, " +
                       "j.jadwal_id, j.waktu_tayang, j.harga_tiket, " +
                       "f.film_id, f.judul, f.poster_url, " +
                       "s.studio_id, s.nama_studio " +
                       "FROM reservasi r " +
                       "JOIN jadwal_tayang j ON r.jadwal_id = j.jadwal_id " +
                       "JOIN film f ON j.film_id = f.film_id " +
                       "JOIN studio s ON j.studio_id = s.studio_id " +
                       "WHERE r.pengguna_id = ? " +
                       "ORDER BY r.waktu_reservasi DESC";

        try (Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // A. Reconstruct Objek Film (Minimal Judul & Poster)
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setJudul(rs.getString("judul"));
                film.setPosterUrl(rs.getString("poster_url"));

                // B. Reconstruct Objek Studio
                Studio studio = new Studio();
                studio.setId(rs.getInt("studio_id"));
                studio.setNamaStudio(rs.getString("nama_studio"));

                // C. Reconstruct Objek Jadwal
                JadwalTayang jadwal = new JadwalTayang();
                jadwal.setId(rs.getInt("jadwal_id"));
                jadwal.setWaktuTayang(rs.getTimestamp("waktu_tayang"));
                jadwal.setHargaTiket(rs.getInt("harga_tiket"));
                jadwal.setFilm(film);
                jadwal.setStudio(studio);

                // D. Reconstruct Objek Reservasi
                Reservasi res = new Reservasi();
                res.setId(rs.getInt("reservasi_id"));
                res.setWaktuReservasi(rs.getTimestamp("waktu_reservasi"));
                res.setTotalHarga(rs.getInt("total_harga"));
                res.setStatusPembayaran(rs.getString("status_pembayaran"));
                res.setJadwal(jadwal);
                
                list.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> getKursiTerisi(int jadwalId) {
        List<Integer> listId = new ArrayList<>();
        // Ambil kursi_id dari detail_reservasi yg terhubung dengan reservasi di jadwal ini
        // Dan statusnya TIDAK batal (bisa 'sukses' atau 'pending')
        String query = "SELECT d.kursi_id FROM detail_reservasi d " +
                        "JOIN reservasi r ON d.reservasi_id = r.reservasi_id " +
                        "WHERE r.jadwal_id = ? AND r.status_pembayaran != 'batal'";
        
        try (Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, jadwalId);
                ResultSet rs = stmt.executeQuery();

                while(rs.next()) {
                    listId.add(rs.getInt("kursi_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return listId;
    }

    public List<Reservasi> getAllReservations() {
        List<Reservasi> list = new ArrayList<>();
        // Join ke tabel Pengguna juga untuk lihat siapa yang pesan
        String query = "SELECT r.reservasi_id, r.waktu_reservasi, r.total_harga, r.status_pembayaran, " +
                       "p.nama_lengkap, " + // Ambil nama user
                       "j.jadwal_id, f.judul, s.nama_studio " +
                       "FROM reservasi r " +
                       "JOIN pengguna p ON r.pengguna_id = p.pengguna_id " +
                       "JOIN jadwal_tayang j ON r.jadwal_id = j.jadwal_id " +
                       "JOIN film f ON j.film_id = f.film_id " +
                       "JOIN studio s ON j.studio_id = s.studio_id " +
                       "ORDER BY r.waktu_reservasi DESC";

        try (Connection conn = KoneksiDatabase.getKoneksi();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Kita buat objek user dummy cuma buat nampung nama
                    pbo.kelompok4.model.User user = new pbo.kelompok4.model.User();
                    user.setNamaLengkap(rs.getString("nama_lengkap"));

                    Film film = new Film();
                    film.setJudul(rs.getString("judul"));

                    Studio studio = new Studio();
                    studio.setNamaStudio(rs.getString("nama_studio"));

                    JadwalTayang jadwal = new JadwalTayang();
                    jadwal.setFilm(film);
                    jadwal.setStudio(studio);

                    Reservasi res = new Reservasi();
                    res.setId(rs.getInt("reservasi_id"));
                    res.setWaktuReservasi(rs.getTimestamp("waktu_reservasi"));
                    res.setTotalHarga(rs.getInt("total_harga"));
                    res.setStatusPembayaran(rs.getString("status_pembayaran"));
                    res.setPengguna(user); // Set user pemesan
                    res.setJadwal(jadwal);
                    
                    list.add(res);
                }
            } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}