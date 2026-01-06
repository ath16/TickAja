package pbo.kelompok4.dao;

import pbo.kelompok4.model.Film;
import pbo.kelompok4.util.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

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
}