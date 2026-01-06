package pbo.kelompok4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import pbo.kelompok4.model.Kursi;
import pbo.kelompok4.model.Reservasi;
import pbo.kelompok4.util.KoneksiDatabase;

public class ReservasiDAO {

    // Method Transaksi Utama: Simpan Header Reservasi + Detail Kursi
    public boolean createReservasi(Reservasi reservasi, List<Kursi> listKursiDipilih) {
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) return false;

        PreparedStatement stmtHeader = null;
        PreparedStatement stmtDetail = null;
        ResultSet generatedKeys = null;

        try {
            // 1. Matikan Auto-Commit (Mulai Transaksi Manual)
            conn.setAutoCommit(false);

            // ---------------------------------------------------------
            // LANGKAH 1: Insert Header Reservasi
            // ---------------------------------------------------------
            String sqlHeader = "INSERT INTO reservasi (pengguna_id, jadwal_id, total_harga, status_pembayaran) VALUES (?, ?, ?, ?)";
            
            // RETURN_GENERATED_KEYS penting untuk mengambil ID reservasi yang baru saja dibuat
            stmtHeader = conn.prepareStatement(sqlHeader, Statement.RETURN_GENERATED_KEYS);
            
            stmtHeader.setInt(1, reservasi.getPengguna().getId());
            stmtHeader.setInt(2, reservasi.getJadwal().getId()); // Asumsi JadwalTayang punya getId()
            stmtHeader.setInt(3, reservasi.getTotalHarga());
            stmtHeader.setString(4, "sukses"); // Langsung sukses atau 'pending' tergantung logika bayar

            int affectedRows = stmtHeader.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Gagal membuat reservasi, tidak ada baris yang terubah.");
            }

            // Ambil ID Reservasi yang baru dibuat (Auto Increment)
            int reservasiIdBaru = 0;
            generatedKeys = stmtHeader.getGeneratedKeys();
            if (generatedKeys.next()) {
                reservasiIdBaru = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Gagal mengambil ID reservasi baru.");
            }

            // ---------------------------------------------------------
            // LANGKAH 2: Insert Detail Reservasi (Looping Kursi)
            // ---------------------------------------------------------
            String sqlDetail = "INSERT INTO detail_reservasi (reservasi_id, kursi_id) VALUES (?, ?)";
            stmtDetail = conn.prepareStatement(sqlDetail);

            for (Kursi kursi : listKursiDipilih) {
                stmtDetail.setInt(1, reservasiIdBaru);
                stmtDetail.setInt(2, kursi.getId());
                stmtDetail.addBatch(); // Kumpulkan query dalam batch biar cepat
            }

            // Eksekusi semua insert kursi sekaligus
            stmtDetail.executeBatch();

            // ---------------------------------------------------------
            // LANGKAH 3: Commit (Simpan Permanen)
            // ---------------------------------------------------------
            conn.commit();
            System.out.println("Transaksi Reservasi Berhasil! ID: " + reservasiIdBaru);
            return true;

        } catch (SQLException e) {
            // Jika ada ERROR, batalkan semua perubahan (Rollback)
            try {
                if (conn != null) conn.rollback();
                System.err.println("Transaksi Dibatalkan (Rollback): " + e.getMessage());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            // Tutup resource manual karena kita matikan auto-close dari try-with-resources untuk kontrol transaksi
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (stmtHeader != null) stmtHeader.close();
                if (stmtDetail != null) stmtDetail.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Kembalikan ke mode default
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}