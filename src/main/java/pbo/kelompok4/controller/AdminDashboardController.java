package pbo.kelompok4.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pbo.kelompok4.App;
import pbo.kelompok4.dao.FilmDAO;
import pbo.kelompok4.dao.JadwalDAO;
import pbo.kelompok4.dao.LokasiDAO;
import pbo.kelompok4.model.Film;
import pbo.kelompok4.model.JadwalTayang;
import pbo.kelompok4.model.Studio;
import pbo.kelompok4.util.Session;

public class AdminDashboardController implements Initializable {

    // --- TAB 1: DATA FILM ---
    @FXML private TextField txtJudul;
    @FXML private TextField txtDeskripsi;
    @FXML private TextField txtDurasi;
    @FXML private TextField txtPoster;
    @FXML private TableView<Film> tabelFilm;
    @FXML private TableColumn<Film, String> colJudul;
    @FXML private TableColumn<Film, Integer> colDurasi;

    // --- TAB 2: JADWAL TAYANG ---
    @FXML private ComboBox<Film> comboFilm;
    @FXML private ComboBox<Studio> comboStudio;
    @FXML private TextField txtWaktu;
    @FXML private TextField txtHarga;
    @FXML private TableView<JadwalTayang> tabelJadwal;
    @FXML private TableColumn<JadwalTayang, String> colJadwalFilm;
    @FXML private TableColumn<JadwalTayang, String> colJadwalStudio;
    @FXML private TableColumn<JadwalTayang, String> colJadwalWaktu;

    // DAO
    private FilmDAO filmDAO = new FilmDAO();
    private JadwalDAO jadwalDAO = new JadwalDAO();
    private LokasiDAO lokasiDAO = new LokasiDAO(); 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTabelFilm();
        setupTabelJadwal();
        
        loadDataFilm();
        loadDataJadwal();
        loadComboData();
    }

    // --- LOGIKA FILM ---
    private void setupTabelFilm() {
        colJudul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        colDurasi.setCellValueFactory(new PropertyValueFactory<>("durasiMenit")); 
    }

    private void loadDataFilm() {
        List<Film> list = filmDAO.getAllFilms();
        ObservableList<Film> observableList = FXCollections.observableArrayList(list);
        tabelFilm.setItems(observableList);
    }

    @FXML
    private void handleSimpanFilm() {
        try {
            String judul = txtJudul.getText();
            String deskripsi = txtDeskripsi.getText();
            String durasiText = txtDurasi.getText();
            String poster = txtPoster.getText();

            if (judul.isEmpty() || durasiText.isEmpty()) {
                showAlert(AlertType.WARNING, "Peringatan", "Judul dan Durasi harus diisi!");
                return;
            }

            int durasi = Integer.parseInt(durasiText);

            Film filmBaru = new Film(0, judul, deskripsi, durasi, poster);
            filmDAO.tambahFilm(filmBaru);
            
            showAlert(AlertType.INFORMATION, "Sukses", "Film berhasil ditambahkan!");
            clearFormFilm();
            loadDataFilm();
            loadComboData(); // PENTING: Refresh combo box film di tab sebelah agar film baru muncul
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Durasi harus berupa angka!");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Gagal menyimpan film: " + e.getMessage());
        }
    }

    private void clearFormFilm() {
        txtJudul.clear();
        txtDeskripsi.clear();
        txtDurasi.clear();
        txtPoster.clear();
    }

    // --- LOGIKA JADWAL ---
    private void setupTabelJadwal() {
        colJadwalFilm.setCellValueFactory(new PropertyValueFactory<>("judulFilm")); 
        colJadwalStudio.setCellValueFactory(new PropertyValueFactory<>("namaStudio"));
        colJadwalWaktu.setCellValueFactory(new PropertyValueFactory<>("waktuTayang"));
    }

    private void loadDataJadwal() {
        List<JadwalTayang> list = jadwalDAO.getAllJadwal();
        ObservableList<JadwalTayang> observableList = FXCollections.observableArrayList(list);
        tabelJadwal.setItems(observableList);
    }

    private void loadComboData() {
        // Load Film ke Combo
        List<Film> films = filmDAO.getAllFilms();
        comboFilm.setItems(FXCollections.observableArrayList(films));

        // Load Studio ke Combo
        List<Studio> studios = lokasiDAO.getAllStudio(); 
        comboStudio.setItems(FXCollections.observableArrayList(studios));
    }

    @FXML
    private void handleSimpanJadwal() {
        try {
            Film film = comboFilm.getValue();
            Studio studio = comboStudio.getValue();
            String waktu = txtWaktu.getText(); // Format: YYYY-MM-DD HH:MM:SS
            String hargaText = txtHarga.getText();

            if (film == null || studio == null || waktu.isEmpty() || hargaText.isEmpty()) {
                showAlert(AlertType.WARNING, "Peringatan", "Mohon lengkapi semua data jadwal!");
                return;
            }

            int harga = Integer.parseInt(hargaText);

            // Simpan lewat DAO
            jadwalDAO.tambahJadwal(film.getFilmId(), studio.getId(), waktu, harga);
            
            showAlert(AlertType.INFORMATION, "Sukses", "Jadwal berhasil diterbitkan!");
            loadDataJadwal();
            
            // Clear input
            txtWaktu.clear();
            txtHarga.clear();
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Harga harus berupa angka!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Gagal simpan jadwal. Pastikan format waktu benar (YYYY-MM-DD HH:MM:SS).");
        }
    }

    // --- LOGOUT ---
    @FXML
    private void handleLogout() throws IOException {
        Session.logout();
        App.setRoot("Login");
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}