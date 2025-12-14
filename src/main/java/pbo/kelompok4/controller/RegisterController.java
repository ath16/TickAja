package pbo.kelompok4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPhone;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private PasswordField pfConfirmPassword;

    public void initialize() {
        System.out.println("RegisterController loaded.");
    }
}
