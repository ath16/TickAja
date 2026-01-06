package pbo.kelompok4.controller;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    private GridPane filmContainer; // Pastikan di FXML ada GridPane dengan fx:id ini

    private FilmDAO filmDAO = new FilmDAO();

    public void initialize() {
        // Tampilkan sapaan user
        Pengguna user = Session.getUser();
        if (user != null) {
            lblWelcome.setText("Selamat Datang, " + user.getNamaLengkap() + "!");
        }

        // Load daftar film
        loadFilms();
    }

    private void loadFilms() {
        List<Film> films = filmDAO.getAllFilms();
        
        int column = 0;
        int row = 1;

        try {
            for (Film film : films) {
                // Membuat Tampilan Kartu Film secara Coding (Dynamic)
                VBox card = createFilmCard(film);

                // Tambahkan ke Grid (col, row)
                filmContainer.add(card, column, row);
                
                column++;
                // Jika sudah 3 kolom, pindah ke baris baru
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
        // 1. Setup VBox (Kartu)
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        box.setPrefWidth(200);

        // 2. Setup Gambar Poster
        ImageView imageView = new ImageView();
        try {
            // Asumsi gambar ada di folder resources/images/
            // Jika file tidak ada, pakai placeholder
            String imagePath = getClass().getResource("/pbo/kelompok4/images/" + film.getPosterUrl()).toExternalForm();
            imageView.setImage(new Image(imagePath));
        } catch (Exception e) {
            // Gambar default jika error/tidak ditemukan
            // imageView.setImage(new Image("...")); 
        }
        imageView.setFitWidth(150);
        imageView.setFitHeight(220);
        imageView.setPreserveRatio(true);

        // 3. Setup Judul
        Label title = new Label(film.getJudul());
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        title.setWrapText(true);

        // 4. Masukkan komponen ke VBox
        box.getChildren().addAll(imageView, title);

        // 5. Event Klik: Pindah ke Detail
        box.setOnMouseClicked(event -> showFilmDetail(film));

        return box;
    }

    private void showFilmDetail(Film film) {
        try {
            // Load FXML Detail
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/FilmDetail.fxml"));
            Parent root = loader.load();

            // Ambil Controller-nya & Kirim Data Film
            FilmDetailController controller = loader.getController();
            controller.setFilm(film);

            // Pindah Scene
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleLogout() throws IOException {
        Session.logout();
        App.setRoot("Login");
    }

    @FXML
    private void handleOrderHistory() throws IOException {
        App.setRoot("OrderHistory");
    }
}