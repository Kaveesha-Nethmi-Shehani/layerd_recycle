package lk.ijse.recycle.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.recycle.bo.BOFactory;
import lk.ijse.recycle.bo.custom.CustomerBO;
import lk.ijse.recycle.bo.custom.PaymentBO;
import lk.ijse.recycle.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.recycle.dto.PaymentDto;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML private Label lblPayment_Id;
    @FXML private TextField txtOrder_Id;
    @FXML private TextField txtInvoice;
    @FXML private TextField txtPayment_Value;
    
    @FXML private TableView<PaymentDto> tblPayment;
    @FXML private TableColumn<PaymentDto, Integer> colPayment_Id;
    @FXML private TableColumn<PaymentDto, Integer> colOrder_Id;
    @FXML private TableColumn<PaymentDto, String> colInvoice;
    @FXML private TableColumn<PaymentDto, Double> colPayment_Value;

   // private final PaymentModel paymentModel = new PaymentModel();
    private int selectedPaymentId = -1;

    //private final PaymentDAOImpl paymentDAO =  new PaymentDAOImpl();

    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.Payment);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllPayments();
        loadNextPaymentId();
        
        tblPayment.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                selectedPaymentId = newVal.getPaymentId();
                lblPayment_Id.setText(String.valueOf(newVal.getPaymentId()));
                txtOrder_Id.setText(String.valueOf(newVal.getOrderId()));
                txtInvoice.setText(newVal.getInvoice());
                txtPayment_Value.setText(String.valueOf(newVal.getPaymentValue()));
            }
        });

        txtOrder_Id.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                try {
                    int orderId = Integer.parseInt(newVal);
                    double value = paymentBO .getOrderTotalPaymentValue(orderId);
                    txtPayment_Value.setText(String.valueOf(value));
                } catch (Exception ignored) {
                    txtPayment_Value.clear();
                }
            } else {
                txtPayment_Value.clear();
            }
        });
    }

    private void setCellValueFactory() {
        if (colPayment_Id == null || colOrder_Id == null || colInvoice == null || colPayment_Value == null) {
            return;
        }
        colPayment_Id.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colOrder_Id.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colInvoice.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        colPayment_Value.setCellValueFactory(new PropertyValueFactory<>("paymentValue"));
    }

    private void loadNextPaymentId() {
        try {
            int nextId = paymentBO.getNextPaymentId();
            lblPayment_Id.setText(String.valueOf(nextId));
            selectedPaymentId = -1;
        } catch (SQLException e) {
            lblPayment_Id.setText("N/A");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllPayments() {
        if (tblPayment == null) {
            return;
        }
        try {
            tblPayment.setItems(FXCollections.observableArrayList(paymentBO.getAllPayment()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load payments: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            if (txtOrder_Id.getText().isEmpty() || txtPayment_Value.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill in all required fields!").show();
                return;
            }
            
                PaymentDto dto = new PaymentDto(
                    0,
                    Integer.parseInt(txtOrder_Id.getText()),
                    txtInvoice.getText(),
                    Double.parseDouble(txtPayment_Value.getText()),
                    java.time.LocalDate.now().toString()
                );
            if (paymentBO.savePayment(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Saved!").show();
                loadAllPayments();
                clearFields();
                loadNextPaymentId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save Failed!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input! Check Order ID and Payment Value.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (selectedPaymentId <= 0) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment from the table to update!").show();
            return;
        }
        
        try {
                PaymentDto dto = new PaymentDto(
                    selectedPaymentId,
                    Integer.parseInt(txtOrder_Id.getText()),
                    txtInvoice.getText(),
                    Double.parseDouble(txtPayment_Value.getText()),
                    java.time.LocalDate.now().toString()
                );
            if (paymentBO.updatePayment(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Updated!").show();
                loadAllPayments();
                clearFields();
                loadNextPaymentId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update Failed!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (selectedPaymentId <= 0) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment from the table to delete!").show();
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this payment?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (paymentBO.deletePayment(selectedPaymentId)) {
                        new Alert(Alert.AlertType.INFORMATION, "Payment Deleted!").show();
                        loadAllPayments();
                        clearFields();
                        loadNextPaymentId();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Delete Failed!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        clearFields();
        loadNextPaymentId();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String invoice = txtInvoice.getText().trim();
        if (invoice.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter an Invoice number to search!").show();
            return;
        }

        for (PaymentDto dto : tblPayment.getItems()) {
            if (dto.getInvoice() != null && dto.getInvoice().equalsIgnoreCase(invoice)) {
                tblPayment.getSelectionModel().select(dto);
                selectedPaymentId = dto.getPaymentId();
                lblPayment_Id.setText(String.valueOf(dto.getPaymentId()));
                txtOrder_Id.setText(String.valueOf(dto.getOrderId()));
                txtPayment_Value.setText(String.valueOf(dto.getPaymentValue()));
                return;
            }
        }
        new Alert(Alert.AlertType.INFORMATION, "Payment not found with Invoice: " + invoice).show();
    }

    private void clearFields() {
        if (txtOrder_Id != null) txtOrder_Id.clear();
        if (txtInvoice != null) txtInvoice.clear();
        if (txtPayment_Value != null) txtPayment_Value.clear();
        if (tblPayment != null) tblPayment.getSelectionModel().clearSelection();
        selectedPaymentId = -1;
    }
}
