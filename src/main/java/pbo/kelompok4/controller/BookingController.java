package pbo.kelompok4.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import pbo.kelompok4.App;
import pbo.kelompok4.dao.KursiDAO; // Tambahan
import pbo.kelompok4.dao.ReservasiDAO;
import pbo.kelompok4.model.JadwalTayang;
import pbo.kelompok4.model.Kursi;
import pbo.kelompok4.model.Pengguna;
import pbo.kelompok4.model.Reservasi;
import pbo.kelompok4.model.User;
import pbo.kelompok4.util.Session;

public class BookingController {

    @FXML private Label lblJudulFilm;
    @FXML private Label lblInfoJadwal;
    @FXML private Label lblTotalHarga;
    @FXML private GridPane gridKursi; 
    @FXML private Button btnPesan;

    private JadwalTayang jadwalDipilih;
    private List<Kursi> listKursiDipilih = new ArrayList<>();
    private ReservasiDAO reservasiDAO = new ReservasiDAO();
    private KursiDAO kursiDAO = new KursiDAO(); // Tambahan DAO

    public void initialize() {
        // Kosongkan, kita load kursi saat setJadwal dipanggil
    }

    public void setJadwal(JadwalTayang jadwal) {
        this.jadwalDipilih = jadwal;
        
        if (jadwal != null) {
            lblJudulFilm.setText(jadwal.getFilm().getJudul());
            lblInfoJadwal.setText(jadwal.getStudio().getNamaStudio() + " - " + jadwal.getWaktuTayang());
            
            // LOAD KURSI DARI DATABASE BERDASARKAN STUDIO
            loadKursiFromDB(jadwal.getStudio().getId());
        }
    }

    private void loadKursiFromDB(int studioId) {
        gridKursi.getChildren().clear();
        
        // Ambil data asli dari DB
        List<Kursi> listKursiDB = kursiDAO.getKursiByStudio(studioId);

        if (listKursiDB.isEmpty()) {
            System.out.println("Tidak ada data kursi di database untuk studio ID: " + studioId);
            return;
        }

        // Logic sederhana untuk menata Grid (Asumsi 8 kursi per baris)
        // Jika mau lebih rapi, bisa pakai logic mapping Baris A->0, B->1 dst.
        for (Kursi kursiData : listKursiDB) {
            String kodeKursi = kursiData.getNomorBaris() + kursiData.getNomorKursi();
            
            ToggleButton btnKursi = new ToggleButton(kodeKursi);
            btnKursi.setPrefSize(50, 40);

            btnKursi.setOnAction(e -> {
                if (btnKursi.isSelected()) {
                    listKursiDipilih.add(kursiData);
                    btnKursi.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white;");
                } else {
                    listKursiDipilih.remove(kursiData);
                    btnKursi.setStyle(""); 
                }
                updateTotalHarga();
            });

            // Tentukan posisi Grid berdasarkan Baris dan Nomor
            // A -> Row 0, B -> Row 1, dst.
            int row = kursiData.getNomorBaris().charAt(0) - 'A'; 
            int col = kursiData.getNomorKursi() - 1;
            
            gridKursi.add(btnKursi, col, row);
        }
    }

    private void updateTotalHarga() {
        if (jadwalDipilih == null) return;
        int total = listKursiDipilih.size() * jadwalDipilih.getHargaTiket();
        lblTotalHarga.setText("Rp " + total);
        btnPesan.setDisable(listKursiDipilih.isEmpty());
    }

    @FXML
    private void handlePesan() {
        Pengguna currentUser = Session.getUser();

        if (currentUser == null || !(currentUser instanceof User)) {
            showAlert(AlertType.WARNING, "Belum Login", "Silakan login sebagai User terlebih dahulu.");
            try { App.setRoot("Login"); } catch (IOException e) { e.printStackTrace(); }
            return;
        }

        User user = (User) currentUser;
        int totalHarga = listKursiDipilih.size() * jadwalDipilih.getHargaTiket();

        Reservasi reservasiBaru = new Reservasi();
        reservasiBaru.setPengguna(user);
        reservasiBaru.setJadwal(jadwalDipilih);
        reservasiBaru.setTotalHarga(totalHarga);
        reservasiBaru.setWaktuReservasi(new Timestamp(System.currentTimeMillis()));
        reservasiBaru.setStatusPembayaran("sukses"); 

        boolean sukses = reservasiDAO.createReservasi(reservasiBaru, listKursiDipilih);

        if (sukses) {
            showAlert(AlertType.INFORMATION, "Berhasil!", "Tiket berhasil dipesan.");
            try { App.setRoot("UserDashboard"); } catch (IOException e) { e.printStackTrace(); }
        } else {
            showAlert(AlertType.ERROR, "Gagal", "Terjadi kesalahan sistem.");
        }
    }
    
    @FXML
    private void handleKembali() throws IOException {
        App.setRoot("UserDashboard"); 
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}