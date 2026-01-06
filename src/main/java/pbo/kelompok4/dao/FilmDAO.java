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

    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        String query = "SELECT * FROM film";
        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                films.add(new Film(
                    rs.getInt("film_id"), rs.getString("judul"),
                    rs.getString("deskripsi"), rs.getInt("durasi_menit"),
                    rs.getString("poster_url")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return films;
    }

    public void tambahFilm(Film f) {
        String sql = "INSERT INTO film (judul, deskripsi, durasi_menit, poster_url) VALUES (?, ?, ?, ?)";
        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getJudul());
            ps.setString(2, f.getDeskripsi());
            ps.setInt(3, f.getDurasiMenit());
            ps.setString(4, f.getPosterUrl());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- BARU: UPDATE FILM ---
    public void updateFilm(Film f) {
        String sql = "UPDATE film SET judul=?, deskripsi=?, durasi_menit=?, poster_url=? WHERE film_id=?";
        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getJudul());
            ps.setString(2, f.getDeskripsi());
            ps.setInt(3, f.getDurasiMenit());
            ps.setString(4, f.getPosterUrl());
            ps.setInt(5, f.getFilmId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void hapusFilm(int id) {
        String sql = "DELETE FROM film WHERE film_id = ?";
        try (Connection conn = KoneksiDatabase.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}