package pbo.kelompok4.controller;

import java.io.IOException;
import java.net.URL;
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
import pbo.kelompok4.dao.PenggunaDAO;
import pbo.kelompok4.dao.ReservasiDAO;
import pbo.kelompok4.model.Film;
import pbo.kelompok4.model.JadwalTayang;
import pbo.kelompok4.model.Reservasi;
import pbo.kelompok4.model.Studio;
import pbo.kelompok4.model.User;
import pbo.kelompok4.util.Session;

public class AdminDashboardController implements Initializable {

    // --- TAB 1: FILM ---
    @FXML private TextField txtJudul, txtDeskripsi, txtDurasi, txtPoster;
    @FXML private TableView<Film> tabelFilm;
    @FXML private TableColumn<Film, String> colJudul, colDeskripsi;
    @FXML private TableColumn<Film, Integer> colDurasi;
    private Film selectedFilm; // Menyimpan film yang sedang diklik di tabel

    // --- TAB 2: JADWAL ---
    @FXML private ComboBox<Film> comboFilm;
    @FXML private ComboBox<Studio> comboStudio;
    @FXML private TextField txtWaktu, txtHarga;
    @FXML private TableView<JadwalTayang> tabelJadwal;
    @FXML private TableColumn<JadwalTayang, String> colJadwalFilm, colJadwalStudio, colJadwalWaktu;
    private JadwalTayang selectedJadwal;

    // --- TAB 3: USER (BARU) ---
    @FXML private TextField txtUserNama, txtUserEmail, txtUserPhone, txtUserPass;
    @FXML private TableView<User> tabelUser;
    @FXML private TableColumn<User, Integer> colUserId;
    @FXML private TableColumn<User, String> colUserNama, colUserEmail, colUserPhone;
    private User selectedUser;

    // --- TAB 4: TRANSAKSI (BARU) ---
    @FXML private TableView<Reservasi> tabelTransaksi;
    @FXML private TableColumn<Reservasi, Integer> colTransID, colTransTotal;
    @FXML private TableColumn<Reservasi, String> colTransUser, colTransFilm, colTransStudio, colTransWaktu, colTransStatus;

    // DAOs
    private FilmDAO filmDAO = new FilmDAO();
    private JadwalDAO jadwalDAO = new JadwalDAO();
    private LokasiDAO lokasiDAO = new LokasiDAO();
    private PenggunaDAO penggunaDAO = new PenggunaDAO();
    private ReservasiDAO reservasiDAO = new ReservasiDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFilmTab();
        initJadwalTab();
        initUserTab();
        initTransaksiTab();
    }

    // ================== LOGIC FILM ==================
    private void initFilmTab() {
        colJudul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        colDurasi.setCellValueFactory(new PropertyValueFactory<>("durasiMenit"));
        colDeskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        loadFilms();

        // Listener: Jika baris tabel diklik, isi form
        tabelFilm.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedFilm = newSelection;
                txtJudul.setText(selectedFilm.getJudul());
                txtDeskripsi.setText(selectedFilm.getDeskripsi());
                txtDurasi.setText(String.valueOf(selectedFilm.getDurasiMenit()));
                txtPoster.setText(selectedFilm.getPosterUrl());
            }
        });
    }

    private void loadFilms() {
        tabelFilm.setItems(FXCollections.observableArrayList(filmDAO.getAllFilms()));
    }

    @FXML private void handleSimpanFilm() {
        try {
            Film f = new Film(0, txtJudul.getText(), txtDeskripsi.getText(), Integer.parseInt(txtDurasi.getText()), txtPoster.getText());
            filmDAO.tambahFilm(f);
            refreshAll();
            handleClearFilm();
            showAlert(AlertType.INFORMATION, "Sukses", "Film berhasil ditambah.");
        } catch (Exception e) { showAlert(AlertType.ERROR, "Error", "Cek inputan anda."); }
    }

    @FXML private void handleEditFilm() {
        if (selectedFilm == null) { showAlert(AlertType.WARNING, "Pilih Film", "Klik salah satu film di tabel dulu."); return; }
        try {
            selectedFilm.setJudul(txtJudul.getText());
            selectedFilm.setDeskripsi(txtDeskripsi.getText());
            selectedFilm.setDurasiMenit(Integer.parseInt(txtDurasi.getText()));
            selectedFilm.setPosterUrl(txtPoster.getText());
            filmDAO.updateFilm(selectedFilm);
            refreshAll();
            handleClearFilm();
            showAlert(AlertType.INFORMATION, "Sukses", "Film berhasil diupdate.");
        } catch (Exception e) { showAlert(AlertType.ERROR, "Error", "Gagal update."); }
    }

    @FXML private void handleHapusFilm() {
        if (selectedFilm == null) return;
        filmDAO.hapusFilm(selectedFilm.getFilmId());
        refreshAll();
        handleClearFilm();
        showAlert(AlertType.INFORMATION, "Sukses", "Film dihapus.");
    }

    @FXML private void handleClearFilm() {
        txtJudul.clear(); txtDeskripsi.clear(); txtDurasi.clear(); txtPoster.clear();
        selectedFilm = null;
        tabelFilm.getSelectionModel().clearSelection();
    }

    // ================== LOGIC JADWAL ==================
    private void initJadwalTab() {
        colJadwalFilm.setCellValueFactory(new PropertyValueFactory<>("judulFilm"));
        colJadwalStudio.setCellValueFactory(new PropertyValueFactory<>("namaStudio"));
        colJadwalWaktu.setCellValueFactory(new PropertyValueFactory<>("waktuTayang"));
        
        loadJadwalData();
        loadComboData();

        tabelJadwal.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedJadwal = newVal;
                // Note: ComboBox agak tricky diset otomatis, kita biarkan kosong atau set manual
            }
        });
    }

    private void loadJadwalData() {
        tabelJadwal.setItems(FXCollections.observableArrayList(jadwalDAO.getAllJadwal()));
    }

    private void loadComboData() {
        comboFilm.setItems(FXCollections.observableArrayList(filmDAO.getAllFilms()));
        comboStudio.setItems(FXCollections.observableArrayList(lokasiDAO.getAllStudio()));
    }

    @FXML private void handleSimpanJadwal() {
        try {
            jadwalDAO.tambahJadwal(comboFilm.getValue().getFilmId(), comboStudio.getValue().getId(), txtWaktu.getText(), Integer.parseInt(txtHarga.getText()));
            refreshAll();
            showAlert(AlertType.INFORMATION, "Sukses", "Jadwal terbit.");
        } catch (Exception e) { showAlert(AlertType.ERROR, "Error", "Cek inputan."); }
    }

    @FXML private void handleHapusJadwal() {
        if (selectedJadwal == null) return;
        jadwalDAO.hapusJadwal(selectedJadwal.getId());
        refreshAll();
        showAlert(AlertType.INFORMATION, "Sukses", "Jadwal dihapus.");
    }

    // ================== LOGIC USER ==================
    private void initUserTab() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserNama.setCellValueFactory(new PropertyValueFactory<>("namaLengkap"));
        colUserEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUserPhone.setCellValueFactory(new PropertyValueFactory<>("noTelepon"));
        loadUsers();

        tabelUser.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedUser = newVal;
                txtUserNama.setText(newVal.getNamaLengkap());
                txtUserEmail.setText(newVal.getEmail());
                txtUserPhone.setText(newVal.getNoTelepon());
                txtUserPass.setText(newVal.getPassword());
            }
        });
    }

    private void loadUsers() {
        tabelUser.setItems(FXCollections.observableArrayList(penggunaDAO.getAllUsers()));
    }

    @FXML private void handleEditUser() {
        if (selectedUser == null) return;
        selectedUser.setNamaLengkap(txtUserNama.getText());
        selectedUser.setEmail(txtUserEmail.getText());
        selectedUser.setNoTelepon(txtUserPhone.getText());
        if(!txtUserPass.getText().isEmpty()) selectedUser.setPassword(txtUserPass.getText());
        
        penggunaDAO.updateUser(selectedUser);
        loadUsers();
        handleClearUser();
        showAlert(AlertType.INFORMATION, "Sukses", "Data user diupdate.");
    }

    @FXML private void handleHapusUser() {
        if (selectedUser == null) return;
        penggunaDAO.deleteUser(selectedUser.getId());
        loadUsers();
        handleClearUser();
        showAlert(AlertType.INFORMATION, "Sukses", "User dihapus.");
    }

    @FXML private void handleClearUser() {
        txtUserNama.clear(); txtUserEmail.clear(); txtUserPhone.clear(); txtUserPass.clear();
        selectedUser = null;
    }

    // ================== LOGIC TRANSAKSI ==================
    private void initTransaksiTab() {
        colTransID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTransUser.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPengguna().getNamaLengkap()));
        colTransFilm.setCellValueFactory(new PropertyValueFactory<>("judulFilm"));
        colTransStudio.setCellValueFactory(new PropertyValueFactory<>("namaStudio"));
        colTransWaktu.setCellValueFactory(new PropertyValueFactory<>("waktuReservasi"));
        colTransTotal.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));
        colTransStatus.setCellValueFactory(new PropertyValueFactory<>("statusPembayaran"));
        
        loadAllReservations();
    }

    @FXML private void loadAllReservations() {
        tabelTransaksi.setItems(FXCollections.observableArrayList(reservasiDAO.getAllReservations()));
    }

    // ================== GLOBAL ==================
    private void refreshAll() {
        loadFilms();
        loadJadwalData();
        loadComboData(); // Penting biar combo box film update kalau ada film baru
        loadUsers();
        loadAllReservations();
    }

    @FXML private void handleLogout() throws IOException {
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