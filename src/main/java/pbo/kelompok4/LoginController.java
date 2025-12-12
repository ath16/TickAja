package pbo.kelompok4;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pfPassword;

    public void initialize() {
        System.out.println("LoginController loaded.");
    }
}
