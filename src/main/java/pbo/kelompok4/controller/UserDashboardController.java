package pbo.kelompok4.controller;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pbo.kelompok4.App;
import pbo.kelompok4.dao.FilmDAO;
import pbo.kelompok4.model.Film;
import pbo.kelompok4.util.Session;

public class UserDashboardController {
    
    @FXML
    private Label lblWelcome;
    
    @FXML
    private ListView<Film> lvFilms;

    private FilmDAO filmDAO;

    public void initialize() {
        filmDAO = new FilmDAO();
        
        // Set nama user di header
        if (Session.getUser() != null) {
            lblWelcome.setText("Selamat Datang, " + Session.getUser().getUsername());
        }

        loadFilms();
    }

    private void loadFilms() {
        List<Film> films = filmDAO.getAllFilms();
        lvFilms.getItems().addAll(films);
    }

    @FXML
    private void handleFilmClick() {
        Film selectedFilm = lvFilms.getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
            openFilmDetail(selectedFilm);
        }
    }

    private void openFilmDetail(Film film) {
        try {
            // Kita harus memuat FXML secara manual untuk passing data (Film object)
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/FilmDetails.fxml"));
            Parent root = loader.load();

            // Ambil controller dari view yang baru dimuat
            FilmDetailController controller = loader.getController();
            controller.setFilmData(film); // Kirim data film ke layar sebelah

            // Ganti scene
            Stage stage = (Stage) lvFilms.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}