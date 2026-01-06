package pbo.kelompok4.controller;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pbo.kelompok4.App;
import pbo.kelompok4.dao.JadwalDAO;
import pbo.kelompok4.model.Film;
import pbo.kelompok4.model.JadwalTayang;

public class FilmDetailController {

    @FXML private Label lblJudul;
    @FXML private Label lblDurasi;
    @FXML private TextArea txtDeskripsi; // Gunakan TextArea biar bisa scroll kalau panjang
    @FXML private ImageView imgPoster;
    @FXML private ComboBox<JadwalTayang> comboJadwal; // Dropdown pilih jadwal

    private Film filmSelected;
    private JadwalDAO jadwalDAO = new JadwalDAO();

    // Method ini dipanggil oleh UserDashboardController
    public void setFilm(Film film) {
        this.filmSelected = film;

        // 1. Isi Data UI
        lblJudul.setText(film.getJudul());
        lblDurasi.setText(film.getDurasiMenit() + " Menit");
        txtDeskripsi.setText(film.getDeskripsi());

        try {
            String imagePath = getClass().getResource("/pbo/kelompok4/images/" + film.getPosterUrl()).toExternalForm();
            imgPoster.setImage(new Image(imagePath));
        } catch (Exception e) {
            // Handle jika gambar tidak ada
        }

        // 2. Ambil Jadwal dari Database
        loadJadwal();
    }

    private void loadJadwal() {
        if (filmSelected == null) return;

        List<JadwalTayang> listJadwal = jadwalDAO.getJadwalByFilmId(filmSelected.getFilmId());
        
        // Masukkan ke ComboBox
        ObservableList<JadwalTayang> observableJadwal = FXCollections.observableArrayList(listJadwal);
        comboJadwal.setItems(observableJadwal);
    }

    @FXML
    private void handleBooking() {
        // 1. Validasi: User harus pilih jadwal dulu
        JadwalTayang jadwalDipilih = comboJadwal.getValue();
        
        if (jadwalDipilih == null) {
            showAlert(AlertType.WARNING, "Pilih Jadwal", "Silakan pilih jadwal tayang terlebih dahulu!");
            return;
        }

        // 2. Pindah ke Halaman Booking Kursi
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/Booking.fxml"));
            Parent root = loader.load();

            // Kirim data Jadwal ke BookingController
            BookingController controller = loader.getController();
            controller.setJadwal(jadwalDipilih);

            // Ganti Scene
            Stage stage = (Stage) lblJudul.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleKembali() {
        try {
            // 1. Load Manual file UserDashboard
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/UserDashboard.fxml"));
            Parent root = loader.load();

            // 2. AMBIL STAGE YANG SEDANG AKTIF (Ini kuncinya!)
            // Kita "numpang" ambil stage lewat Label Judul atau komponen apapun yang ada di layar
            Stage currentStage = (Stage) lblJudul.getScene().getWindow();

            // 3. Pasang Dashboard ke Stage tersebut
            currentStage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Gagal kembali ke dashboard: " + e.getMessage());
        }
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}