package pbo.kelompok4.controller;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import pbo.kelompok4.App;
import pbo.kelompok4.dao.FilmDAO;
import pbo.kelompok4.model.Film;
import pbo.kelompok4.model.Pengguna;
import pbo.kelompok4.util.Session;

public class UserDashboardController {

    @FXML
    private Label lblWelcome;

    @FXML
    private GridPane filmContainer;

    private FilmDAO filmDAO = new FilmDAO();

    public void initialize() {
        // Load User Info (Dengan Safety Try-Catch)
        try {
            Pengguna user = Session.getUser();
            if (user != null && lblWelcome != null) {
                lblWelcome.setText("Selamat Datang, " + user.getNamaLengkap() + "!");
            } else if (lblWelcome != null) {
                lblWelcome.setText("Selamat Datang, Pengunjung!");
            }
        } catch (Exception e) {
            System.err.println("Warning: Gagal memuat info user: " + e.getMessage());
            if (lblWelcome != null) lblWelcome.setText("Selamat Datang!");
        }

        // Load Daftar Film
        try {
            loadFilms();
        } catch (Exception e) {
            System.err.println("Warning: Gagal memuat film: " + e.getMessage());
        }
    }

    private void loadFilms() {
        List<Film> films = filmDAO.getAllFilms();
        
        if (films == null || films.isEmpty()) return;

        int column = 0;
        int row = 1;

        try {
            for (Film film : films) {
                VBox card = createFilmCard(film);
                if (filmContainer != null) {
                    filmContainer.add(card, column, row);
                }
                column++;
                if (column == 3) { 
                    column = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createFilmCard(Film film) {
        VBox box = new VBox();

        String normalStyle = "-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);";
        String hoverStyle = "-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 15, 0, 0, 5); -fx-scale-x: 1.02; -fx-scale-y: 1.02;";

        box.setStyle(normalStyle);
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(15);
        box.setPadding(new Insets(15));
        box.setPrefWidth(220);
        box.setPrefHeight(340);
        box.setCursor(javafx.scene.Cursor.HAND);

        box.setOnMouseEntered(e -> box.setStyle(hoverStyle));
        box.setOnMouseExited(e -> box.setStyle(normalStyle));

        ImageView imageView = new ImageView();
        try {
            String posterName = (film.getPosterUrl() != null && !film.getPosterUrl().isEmpty()) 
                                ? film.getPosterUrl() : "default.jpg";
            String imagePath = "";

            if (getClass().getResource("/pbo/kelompok4/images/" + posterName) != null) {
                imagePath = getClass().getResource("/pbo/kelompok4/images/" + posterName).toExternalForm();
                imageView.setImage(new Image(imagePath));
            }
        } catch (Exception e) { }
        imageView.setFitWidth(150);
        imageView.setFitHeight(220);
        imageView.setPreserveRatio(true);

        Label title = new Label(film.getJudul());
        title.setTextFill(Color.web("#2d3436"));
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-font-family: 'System';");
        title.setWrapText(true);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setMaxWidth(190);

        box.getChildren().addAll(imageView, title);
        box.setOnMouseClicked(event -> showFilmDetail(film));

        return box;
    }

    private void showFilmDetail(Film film) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/FilmDetail.fxml"));
            Parent root = loader.load();

            FilmDetailController controller = loader.getController();
            controller.setFilm(film);

            // Ganti Scene Manual
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // --- PERBAIKAN LOGOUT (Manual Stage) ---
    @FXML
    private void handleLogout() {
        try {
            Session.logout();
            
            // Load Login FXML
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/Login.fxml"));
            Parent root = loader.load();

            // Ambil Stage Aktif & Ganti Root
            Stage currentStage = (Stage) lblWelcome.getScene().getWindow();
            currentStage.getScene().setRoot(root);
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error Logout", "Gagal memuat halaman Login.");
        }
    }

    // --- PERBAIKAN HISTORY (Manual Stage) ---
    @FXML
    private void handleOrderHistory() {
        try {
            // Load History FXML
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/OrderHistory.fxml"));
            Parent root = loader.load();

            // Ambil Stage Aktif & Ganti Root
            Stage currentStage = (Stage) lblWelcome.getScene().getWindow();
            currentStage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error Navigasi", "Gagal memuat halaman Riwayat Order.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}