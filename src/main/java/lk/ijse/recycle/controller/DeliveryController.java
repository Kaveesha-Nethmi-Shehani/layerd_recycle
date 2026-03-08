package lk.ijse.recycle.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.recycle.bo.BOFactory;
import lk.ijse.recycle.bo.custom.DeliveryBO;
import lk.ijse.recycle.dao.custom.impl.DeliveryDAOImpl;
import lk.ijse.recycle.dto.DeliveryDto;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeliveryController implements Initializable {

    @FXML private Label lblDelivery_Id;
    @FXML private TextField txtOrder_Id;
    @FXML private TextField txtDetails_Location;

    @FXML private TableView<DeliveryDto> tblDelivery;

    @FXML private TableColumn<DeliveryDto, Integer> colDelivery_Id;
    @FXML private TableColumn<DeliveryDto, Integer> colOrder_Id;
    @FXML private TableColumn<DeliveryDto, String> colDetails_Location;

   // private final DeliveryModel model = new DeliveryModel();

    DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.Delivery);

   // private final DeliveryDAOImpl deliveryDAO = new DeliveryDAOImpl();


    private int selectedDeliveryId = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        loadDeliveryTable();
        loadNextDeliveryId();
        
        tblDelivery.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                selectedDeliveryId = newVal.getDeliveryId();
                lblDelivery_Id.setText(String.valueOf(newVal.getDeliveryId()));
                txtOrder_Id.setText(String.valueOf(newVal.getOrderId()));
                txtDetails_Location.setText(newVal.getDetailsLocation());
            }
        });
    }

    private void setCellValueFactory() {
        if (colDelivery_Id == null || colOrder_Id == null || colDetails_Location == null) {
            return;
        }
        colDelivery_Id.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colOrder_Id.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDetails_Location.setCellValueFactory(new PropertyValueFactory<>("detailsLocation"));
    }

    private void loadNextDeliveryId() {
        try {
            int nextId = deliveryBO.getNextDeliveryId();
            lblDelivery_Id.setText(String.valueOf(nextId));
            selectedDeliveryId = -1;
        } catch (SQLException e) {
            lblDelivery_Id.setText("N/A");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
        }
    }

    private void loadDeliveryTable() {
        if (tblDelivery == null) {
            return;
        }
        try {
            tblDelivery.setItems(FXCollections.observableArrayList(deliveryBO.getAllDelivery()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load deliveries: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            if (txtOrder_Id.getText().isEmpty() || txtDetails_Location.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
                return;
            }
            
            DeliveryDto dto = new DeliveryDto(
                    0,
                    Integer.parseInt(txtOrder_Id.getText()),
                    txtDetails_Location.getText()
            );

            if (deliveryBO.saveDelivery(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery Saved!").show();
                loadDeliveryTable();
                clearFields();
                loadNextDeliveryId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save Failed!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Order ID!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (selectedDeliveryId <= 0) {
            new Alert(Alert.AlertType.WARNING, "Please select a delivery from the table to update!").show();
            return;
        }
        
        try {
            DeliveryDto dto = new DeliveryDto(
                    selectedDeliveryId,
                    Integer.parseInt(txtOrder_Id.getText()),
                    txtDetails_Location.getText()
            );

            if (deliveryBO.updateDelivery(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully!").show();
                loadDeliveryTable();
                clearFields();
                loadNextDeliveryId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update Failed!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (selectedDeliveryId <= 0) {
            new Alert(Alert.AlertType.WARNING, "Please select a delivery from the table to delete!").show();
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this delivery?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (deliveryBO.deleteDelivery(selectedDeliveryId)) {
                        new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully!").show();
                        loadDeliveryTable();
                        clearFields();
                        loadNextDeliveryId();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Delete Failed!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String orderIdText = txtOrder_Id.getText().trim();
        if (orderIdText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter an Order ID to search!").show();
            return;
        }
        
        try {
            int orderId = Integer.parseInt(orderIdText);

            for (DeliveryDto dto : tblDelivery.getItems()) {
                if (dto.getOrderId() == orderId) {
                    tblDelivery.getSelectionModel().select(dto);
                    selectedDeliveryId = dto.getDeliveryId();
                    lblDelivery_Id.setText(String.valueOf(dto.getDeliveryId()));
                    txtDetails_Location.setText(dto.getDetailsLocation());
                    return;
                }
            }
            new Alert(Alert.AlertType.INFORMATION, "Delivery not found for Order ID: " + orderId).show();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Order ID!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        clearFields();
        loadNextDeliveryId();
    }

    private void clearFields() {
        if (txtOrder_Id != null) txtOrder_Id.clear();
        if (txtDetails_Location != null) txtDetails_Location.clear();
        if (tblDelivery != null) tblDelivery.getSelectionModel().clearSelection();
        selectedDeliveryId = -1;
    }
}