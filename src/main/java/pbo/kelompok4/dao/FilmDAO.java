package com.tickaja.dao;

import com.tickaja.model.Film;
import com.tickaja.util.KoneksiDB; // Pastikan file KoneksiDB sudah ada
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {
    
    public void tambahFilm(Film f) {
        String sql = "INSERT INTO film (judul, deskripsi, durasi_menit, poster_url) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql)) {
            ps.setString(1, f.getJudul());
            ps.setString(2, f.getDeskripsi());
            ps.setInt(3, f.getDurasi());
            ps.setString(4, f.getPosterUrl());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Film> getAllFilm() {
        List<Film> list = new ArrayList<>();
        String sql = "SELECT * FROM film";
        try (Statement st = KoneksiDB.getKoneksi().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Film(
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
        return list;
    }

    // Fitur Hapus
    public void hapusFilm(int id) {
        String sql = "DELETE FROM film WHERE film_id = ?";
        try (PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}