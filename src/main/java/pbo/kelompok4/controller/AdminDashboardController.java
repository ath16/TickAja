package com.tickaja.controller;

import com.tickaja.dao.FilmDAO;
import com.tickaja.dao.JadwalDAO;
import com.tickaja.model.Film;
import com.tickaja.model.JadwalTayang;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminDashboardController {
    
    // --- INPUT FILM ---
    @FXML private TextField txtJudul, txtDeskripsi, txtDurasi, txtPoster;
    @FXML private TableView<Film> tabelFilm;
    @FXML private TableColumn<Film, String> colJudul;
    @FXML private TableColumn<Film, Integer> colDurasi;
    
    // --- INPUT JADWAL ---
    @FXML private ComboBox<Film> comboFilm; 
    @FXML private ComboBox<String> comboStudio;
    @FXML private TextField txtWaktu, txtHarga;
    @FXML private TableView<JadwalTayang> tabelJadwal;
    @FXML private TableColumn<JadwalTayang, String> colJadwalFilm, colJadwalStudio, colJadwalWaktu;

    private FilmDAO filmDAO = new FilmDAO();
    private JadwalDAO jadwalDAO = new JadwalDAO();

    @FXML
    public void initialize() {
        // Setup Tabel
        colJudul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        colDurasi.setCellValueFactory(new PropertyValueFactory<>("durasi"));
        
        colJadwalFilm.setCellValueFactory(new PropertyValueFactory<>("judulFilm"));
        colJadwalStudio.setCellValueFactory(new PropertyValueFactory<>("namaStudio"));
        colJadwalWaktu.setCellValueFactory(new PropertyValueFactory<>("waktuTayang"));

        // Setup Dropdown Studio (Manual sesuai data dummy SQL)
        comboStudio.setItems(FXCollections.observableArrayList("Studio 1 (Regular)", "Studio 2 (VIP)", "Studio IMAX"));
        
        loadData();
    }

    private void loadData() {
        ObservableList<Film> dataFilm = FXCollections.observableArrayList(filmDAO.getAllFilm());
        tabelFilm.setItems(dataFilm);
        comboFilm.setItems(dataFilm); 
        
        ObservableList<JadwalTayang> dataJadwal = FXCollections.observableArrayList(jadwalDAO.getAllJadwal());
        tabelJadwal.setItems(dataJadwal);
    }

    @FXML
    private void handleSimpanFilm() {
        try {
            Film f = new Film(0, txtJudul.getText(), txtDeskripsi.getText(), 
                              Integer.parseInt(txtDurasi.getText()), txtPoster.getText());
            filmDAO.tambahFilm(f);
            loadData();
            bersihkanForm();
            showAlert("Sukses", "Data film berhasil disimpan!");
        } catch (Exception e) {
            showAlert("Error", "Cek input durasi (harus angka)!");
        }
    }
    
    @FXML
    private void handleSimpanJadwal() {
        try {
            Film filmDipilih = comboFilm.getValue();
            if (filmDipilih == null || comboStudio.getValue() == null) {
                showAlert("Peringatan", "Pilih Film dan Studio dulu!");
                return;
            }

            int studioId = 1; // Default
            if(comboStudio.getValue().contains("IMAX")) studioId = 3;
            else if(comboStudio.getValue().contains("VIP")) studioId = 2;
            
            jadwalDAO.tambahJadwal(filmDipilih.getId(), studioId, txtWaktu.getText(), Integer.parseInt(txtHarga.getText()));
            loadData();
            showAlert("Sukses", "Jadwal berhasil diterbitkan!");
        } catch (Exception e) {
             showAlert("Error", "Cek input harga/waktu!");
        }
    }
    
    // Fitur Hapus Film (Tambahan untuk CRUD)
    @FXML
    private void handleHapusFilm() {
        Film selected = tabelFilm.getSelectionModel().getSelectedItem();
        if (selected != null) {
            filmDAO.hapusFilm(selected.getId());
            loadData();
        } else {
            showAlert("Peringatan", "Pilih film yang mau dihapus!");
        }
    }

    private void bersihkanForm() {
        txtJudul.clear(); txtDeskripsi.clear(); txtDurasi.clear();
        txtWaktu.clear(); txtHarga.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}