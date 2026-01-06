package pbo.kelompok4.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pbo.kelompok4.App;
import pbo.kelompok4.dao.ReservasiDAO;
import pbo.kelompok4.model.Reservasi;
import pbo.kelompok4.model.User;
import pbo.kelompok4.util.Session;

public class OrderHistoryController implements Initializable {

    @FXML private TableView<Reservasi> tabelOrder;
    @FXML private TableColumn<Reservasi, String> colFilm;
    @FXML private TableColumn<Reservasi, String> colStudio;
    @FXML private TableColumn<Reservasi, String> colWaktu;
    @FXML private TableColumn<Reservasi, Integer> colTotal;
    @FXML private TableColumn<Reservasi, String> colStatus;

    private ReservasiDAO reservasiDAO = new ReservasiDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        loadData();
    }

    private void setupTable() {
        // PropertyValueFactory mencari method getter di class Reservasi
        // Pastikan class Reservasi kamu punya helper method jika ingin ambil data nested (Film/Studio)
        // Atau update query ReservasiDAO agar return object lengkap
        
        // Untuk sederhananya, pastikan Reservasi.java punya getter yang sesuai
        // colFilm.setCellValueFactory(...) -> Perlu penyesuaian di Model Reservasi jika ingin menampilkan nama Film langsung
        // Di sini saya asumsikan Reservasi punya properti dasar dulu
        
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusPembayaran"));
        colWaktu.setCellValueFactory(new PropertyValueFactory<>("waktuReservasi"));

        colFilm.setCellValueFactory(new PropertyValueFactory<>("judulFilm"));
        colStudio.setCellValueFactory(new PropertyValueFactory<>("namaStudio"));
        
        // NOTE: Untuk Judul Film & Studio, idealnya ReservasiDAO melakukan JOIN
        // dan Reservasi.java punya method getJudulFilm().
    }

    private void loadData() {
        // Ambil User yang sedang login
        pbo.kelompok4.model.Pengguna currentUser = Session.getUser();
        
        if (currentUser instanceof User) {
            User user = (User) currentUser;
            // Ambil history milik user tersebut
            List<Reservasi> list = reservasiDAO.getReservasiByUser(user.getId());
            ObservableList<Reservasi> observableList = FXCollections.observableArrayList(list);
            tabelOrder.setItems(observableList);
        }
    }
    
    @FXML
    private void handleKembali() throws IOException {
        App.setRoot("UserDashboard");
    }
}