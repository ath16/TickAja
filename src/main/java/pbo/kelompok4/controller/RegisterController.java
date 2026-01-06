package pbo.kelompok4.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pbo.kelompok4.App;
import pbo.kelompok4.dao.PenggunaDAO;
import pbo.kelompok4.model.User;

public class RegisterController {

    // Komponen FXML

    @FXML
    private TextField tfName; // Nama Lengkap

    @FXML
    private TextField tfUsername; // Username

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPhone; // No Telepon

    @FXML
    private PasswordField pfPassword;

    @FXML
    private PasswordField pfConfirmPassword;

    private PenggunaDAO penggunaDAO;

    // Method ini dipanggil otomatis saat FXML dimuat
    public void initialize() {
        penggunaDAO = new PenggunaDAO();
        System.out.println("RegisterController loaded.");
    }

    // Action Handlers

    @FXML
    private void handleRegister() {
        // Ambil data dari form
        String nama = tfName.getText();
        String username = tfUsername.getText();
        String email = tfEmail.getText();
        String phone = tfPhone.getText();
        String password = pfPassword.getText();
        String confirmPassword = pfConfirmPassword.getText();

        // Validasi Input (Cegah data kosong/salah)
        if (nama.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error", "Harap isi semua kolom data!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(AlertType.ERROR, "Password Error", "Konfirmasi password tidak cocok!");
            return;
        }

        // Validasi Username Unique
        if (penggunaDAO.isUsernameExists(username)) {
            showAlert(AlertType.WARNING, "Username Terpakai", "Username '" + username + "' sudah digunakan. Pilih yang lain.");
            return;
        }

        // Bungkus data ke dalam Object Model (User)
        // ID 0 karena auto-increment di database
        User userBaru = new User(0, username, password, nama, email, phone);

        // Kirim ke Database lewat DAO
        boolean isSuccess = penggunaDAO.registerUser(userBaru);

        if (isSuccess) {
            showAlert(AlertType.INFORMATION, "Berhasil", "Registrasi Berhasil! Silakan Login.");
            try {
                // Pindah ke halaman Login
                App.setRoot("Login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(AlertType.ERROR, "Gagal", "Terjadi kesalahan saat menyimpan ke database.");
        }
    }

    // Tombol link ke Login
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("Login");
    }

    // Helper pesan pop-up
    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}