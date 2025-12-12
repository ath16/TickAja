module pbo.kelompok4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens pbo.kelompok4 to javafx.fxml;
    exports pbo.kelompok4;
}
