package pbo.kelompok4.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pbo.kelompok4.App;
import pbo.kelompok4.dao.PenggunaDAO;
import pbo.kelompok4.model.Pengguna;
import pbo.kelompok4.util.Session;

public class LoginController {

    @FXML
    private TextField tfEmail; // Di FXML namanya tfEmail, tapi kita pakai buat Username sesuai DB
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Label lblError;

    private PenggunaDAO penggunaDAO;

    public void initialize() {
        penggunaDAO = new PenggunaDAO();
    }

    @FXML
    private void handleLogin() throws IOException {
        String username = tfEmail.getText();
        String password = pfPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Username dan Password harus diisi!");
            return;
        }

        Pengguna user = penggunaDAO.login(username, password);

        if (user != null) {
            // Simpan sesi
            Session.setUser(user);
            
            // Cek Role untuk redirect (Sesuai permintaan teroris, kita fokus ke User Dashboard dulu)
            if ("admin".equalsIgnoreCase(user.getRole())) {
                App.setRoot("AdminDashboard");
            } else {
                App.setRoot("UserDashboard"); 
            }
        } else {
            lblError.setText("Username atau Password salah!");
        }
    }
    
    @FXML
    private void handleToRegister() throws IOException {
        App.setRoot("Register");
    }
}