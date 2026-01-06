package com.tickaja.dao;

import com.tickaja.model.Film;
import com.tickaja.model.JadwalTayang;
import com.tickaja.util.KoneksiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class JadwalDAO {

    public void tambahJadwal(int filmId, int studioId, String waktu, int harga) {
        String sql = "INSERT INTO jadwal_tayang (film_id, studio_id, waktu_tayang, harga_tiket) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql)) {
            ps.setInt(1, filmId);
            ps.setInt(2, studioId);
            ps.setString(3, waktu); 
            ps.setInt(4, harga);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<JadwalTayang> getAllJadwal() {
        List<JadwalTayang> list = new ArrayList<>();
        String sql = "SELECT j.jadwal_id, j.waktu_tayang, j.harga_tiket, " +
                     "f.film_id, f.judul, f.deskripsi, f.durasi_menit, f.poster_url, " +
                     "s.studio_id, s.nama_studio " +
                     "FROM jadwal_tayang j " +
                     "JOIN film f ON j.film_id = f.film_id " +
                     "JOIN studio s ON j.studio_id = s.studio_id";
                     
        try (Statement st = KoneksiDB.getKoneksi().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
             
            while (rs.next()) {
                Film f = new Film(
                    rs.getInt("film_id"), rs.getString("judul"), 
                    rs.getString("deskripsi"), rs.getInt("durasi_menit"), 
                    rs.getString("poster_url")
                );
                
                Timestamp ts = rs.getTimestamp("waktu_tayang");
                LocalDateTime waktu = ts.toLocalDateTime();

                list.add(new JadwalTayang(
                    rs.getInt("jadwal_id"), f,
                    rs.getInt("studio_id"), rs.getString("nama_studio"),
                    waktu, rs.getInt("harga_tiket")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}