package pbo.kelompok4.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pbo.kelompok4.App;
import pbo.kelompok4.model.Film;

public class FilmDetailController {
    
    @FXML
    private Label lblJudul;
    @FXML
    private Label lblDurasi;
    @FXML
    private Label lblDeskripsi;

    private Film currentFilm;

    public void initialize() {
        // Kosong saat inisialisasi awal, data diisi via setFilmData
    }

    // Method ini dipanggil dari DashboardController
    public void setFilmData(Film film) {
        this.currentFilm = film;
        lblJudul.setText(film.getJudul());
        lblDurasi.setText("Durasi: " + film.getDurasiMenit() + " Menit");
        lblDeskripsi.setText(film.getDeskripsi());
    }

    @FXML
    private void handleBack() throws IOException {
        App.setRoot("UserDashboard");
    }
}