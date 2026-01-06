package pbo.kelompok4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pbo.kelompok4.model.Film;
import pbo.kelompok4.util.KoneksiDatabase;

public class FilmDAO {

    // 1. Method Ambil Semua Film (Dipakai di User Dashboard & Admin Dashboard)
    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        String query = "SELECT * FROM film";
        
        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                films.add(new Film(
                    rs.getInt("film_id"),
                    rs.getString("judul"),
                    rs.getString("deskripsi"),
                    rs.getInt("durasi_menit"),
                    rs.getString("poster_url")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

    // 2. Method Tambah Film (Fitur Baru dari Branch Byan - Diperbaiki)
    public void tambahFilm(Film f) {
        String sql = "INSERT INTO film (judul, deskripsi, durasi_menit, poster_url) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, f.getJudul());
            ps.setString(2, f.getDeskripsi());
            // Perbaikan: Di Model Film.java namanya getDurasiMenit(), bukan getDurasi()
            ps.setInt(3, f.getDurasiMenit()); 
            ps.setString(4, f.getPosterUrl());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Method Hapus Film (Fitur Baru dari Branch Byan - Diperbaiki)
    public void hapusFilm(int id) {
        String sql = "DELETE FROM film WHERE film_id = ?";
        
        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}