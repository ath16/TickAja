package pbo.kelompok4.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderHistoryController {

    @FXML
    private TableView<OrderHistory> orderTable;

    @FXML
    private TableColumn<OrderHistory, Integer> colIdOrder;

    @FXML
    private TableColumn<OrderHistory, String> colFilm;

    @FXML
    private TableColumn<OrderHistory, String> colLokasi;

    @FXML
    private TableColumn<OrderHistory, String> colStudio;

    @FXML
    private TableColumn<OrderHistory, String> colTanggal;

    @FXML
    private TableColumn<OrderHistory, Integer> colJumlah;

    @FXML
    private TableColumn<OrderHistory, Double> colTotal;

    @FXML
    private Button btnKembali;

    private ObservableList<OrderHistory> orderList =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colIdOrder.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        colFilm.setCellValueFactory(new PropertyValueFactory<>("namaFilm"));
        colLokasi.setCellValueFactory(new PropertyValueFactory<>("namaLokasi"));
        colStudio.setCellValueFactory(new PropertyValueFactory<>("namaStudio"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlahTiket"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));

        
        orderList.add(new OrderHistory(
                1,
                "Avengers",
                "XXI Sun Plaza",
                "Studio 1",
                "2026-01-10",
                2,
                100000
        ));

        orderTable.setItems(orderList);
    }
}



