package lk.ijse.recycle.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
//import lk.ijse.recycle.dao.custom.impl.OrderDAOImpl;
import lk.ijse.recycle.bo.BOFactory;
import lk.ijse.recycle.bo.custom.OrderBO;
import lk.ijse.recycle.dto.OrderDto;
import lk.ijse.recycle.dto.ProductDto;
import lk.ijse.recycle.dto.CustomerDto;
import lk.ijse.recycle.util.ReportUtil;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML private Label lblOrder_Id;
    @FXML private ComboBox<ProductDto> cmbProduct;
    @FXML private ComboBox<CustomerDto> cmbCustomer;
    @FXML private TextField txtQuantity;
    @FXML private Label lblUnit_Price;
    @FXML private Label lblTotal_Price;
    @FXML private Label lblStock_Available;
    @FXML private DatePicker dpOrder_Date;

    @FXML private TableView<OrderDto> tblOrder;
    @FXML private TableColumn<OrderDto, Integer> colOrder_Id;
    @FXML private TableColumn<OrderDto, String> colProduct;
    @FXML private TableColumn<OrderDto, String> colCustomer;
    @FXML private TableColumn<OrderDto, Integer> colQuantity;
    @FXML private TableColumn<OrderDto, Double> colTotal_Price;
    @FXML private TableColumn<OrderDto, String> colOrder_Date;

    //private final OrderModel orderModel = new OrderModel();
    private int selectedOrderId = -1;

   // private final OrderDAOImpl orderDAO = new OrderDAOImpl();

    OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.Order);



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllOrders();
        loadNextOrderId();
        loadProductsComboBox();
        loadCustomersComboBox();
        setupListeners();
        

        if (dpOrder_Date != null) {
            dpOrder_Date.setValue(LocalDate.now());
        }
        
        tblOrder.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                selectedOrderId = newVal.getOrderId();
                lblOrder_Id.setText(String.valueOf(newVal.getOrderId()));
                txtQuantity.setText(String.valueOf(newVal.getQuantity()));
                lblTotal_Price.setText(String.format("LKR %.2f", newVal.getTotalPrice()));
                

                for (ProductDto p : cmbProduct.getItems()) {
                    if (p.getProductId().equals(newVal.getProductId())) {
                        cmbProduct.setValue(p);
                        break;
                    }
                }
                

                for (CustomerDto c : cmbCustomer.getItems()) {
                    if (c.getCustomerId() == newVal.getCustomerId()) {
                        cmbCustomer.setValue(c);
                        break;
                    }
                }
                

                if (newVal.getOrderDate() != null && !newVal.getOrderDate().isEmpty()) {
                    try {
                        dpOrder_Date.setValue(LocalDate.parse(newVal.getOrderDate().split(" ")[0]));
                    } catch (Exception e) {
                        dpOrder_Date.setValue(LocalDate.now());
                    }
                }
            }
        });
    }

    private void setCellValueFactory() {
        if (colOrder_Id == null) return;
        
        colOrder_Id.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotal_Price.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colOrder_Date.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
    }

    private void loadNextOrderId() {
        try {

            int nextId = orderBO.getNextOrderId();
            lblOrder_Id.setText(String.valueOf(nextId));
            selectedOrderId = -1;
        } catch (SQLException | ClassNotFoundException e) {
            lblOrder_Id.setText("N/A");
            e.printStackTrace();
        }
    }

    private void loadAllOrders() {
        if (tblOrder == null) return;
        
        try {
            tblOrder.setItems(FXCollections.observableArrayList(orderBO.getAllOrders()));
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load orders: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadProductsComboBox() {
        if (cmbProduct == null) return;
        
        try {
            cmbProduct.setItems(FXCollections.observableArrayList(orderBO.getAvailableProductsOrder()));
            
            cmbProduct.setConverter(new StringConverter<ProductDto>() {
                @Override
                public String toString(ProductDto p) {
                    if (p == null) return "";
                    return p.getProduct_name() + " - LKR " + p.getSellingPrice() + " (Stock: " + p.getStockQuantity() + ")";
                }

                @Override
                public ProductDto fromString(String string) {
                    return null;
                }
            });
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load products: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomersComboBox() {
        if (cmbCustomer == null) return;

        try {

            cmbCustomer.setItems(FXCollections.observableArrayList(
                    orderBO.getAllCustomersOrder()
            ));

            cmbCustomer.setConverter(new StringConverter<CustomerDto>() {
                @Override
                public String toString(CustomerDto c) {
                    if (c == null) return "";
                    return c.getCustomerName() + " (" + c.getCustomerContact() + ")";
                }

                @Override
                public CustomerDto fromString(String string) {
                    return null;
                }
            });

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customers").show();
            e.printStackTrace();
        }
    }

    private void setupListeners() {

        if (cmbProduct != null) {
            cmbProduct.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    lblUnit_Price.setText(String.format("LKR %.2f", newVal.getSellingPrice()));
                    lblStock_Available.setText(String.valueOf(newVal.getStockQuantity()));
                    calculateTotal();
                } else {
                    lblUnit_Price.setText("LKR 0.00");
                    lblStock_Available.setText("0");
                }
            });
        }


        if (txtQuantity != null) {
            txtQuantity.textProperty().addListener((obs, oldVal, newVal) -> {
                calculateTotal();
            });
        }
    }

    private void calculateTotal() {
        if (cmbProduct == null || txtQuantity == null || lblTotal_Price == null) return;
        
        ProductDto product = cmbProduct.getValue();
        String qtyText = txtQuantity.getText().trim();
        
        if (product == null || qtyText.isEmpty()) {
            lblTotal_Price.setText("LKR 0.00");
            return;
        }
        
        try {
            int qty = Integer.parseInt(qtyText);
            double total = qty * product.getSellingPrice();
            lblTotal_Price.setText(String.format("LKR %.2f", total));
        } catch (NumberFormatException e) {
            lblTotal_Price.setText("Invalid qty");
        }
    }

    private double getCalculatedTotal() {
        ProductDto product = cmbProduct.getValue();
        String qtyText = txtQuantity.getText().trim();
        
        if (product == null || qtyText.isEmpty()) return 0;
        
        try {
            int qty = Integer.parseInt(qtyText);
            return qty * product.getSellingPrice();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        try {
            if (!validateFields()) return;

            ProductDto product = cmbProduct.getValue();
            CustomerDto customer = cmbCustomer.getValue();
            int quantity = Integer.parseInt(txtQuantity.getText().trim());


            if (quantity > product.getStockQuantity()) {
                new Alert(Alert.AlertType.ERROR, 
                    "Insufficient stock!\n" +
                    "Available: " + product.getStockQuantity() + "\n" +
                    "Requested: " + quantity).show();
                return;
            }

            String dateStr = dpOrder_Date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            double totalPrice = getCalculatedTotal();

            OrderDto dto = new OrderDto(
                0,
                product.getProductId(),
                customer.getCustomerId(),
                quantity,
                totalPrice,
                dateStr
            );


            if (orderBO.saveOrder(dto)) {
                new Alert(Alert.AlertType.INFORMATION, 
                    "Order Placed Successfully!\n\n" +
                    "Product: " + product.getProduct_name() + "\n" +
                    "Customer: " + customer.getCustomerName() + "\n" +
                    "Quantity: " + quantity + "\n" +
                    "Total: LKR " + String.format("%.2f", totalPrice)).show();
                
                loadAllOrders();
                loadProductsComboBox();
                clearFields();
                loadNextOrderId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order failed! Check stock availability.").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        if (selectedOrderId <= 0) {
            new Alert(Alert.AlertType.WARNING, "Please select an order to update!").show();
            return;
        }

        try {
            if (!validateFields()) return;

            ProductDto product = cmbProduct.getValue();
            CustomerDto customer = cmbCustomer.getValue();
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            String dateStr = dpOrder_Date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            double totalPrice = getCalculatedTotal();

            OrderDto dto = new OrderDto(
                selectedOrderId,
                product.getProductId(),
                customer.getCustomerId(),
                quantity,
                totalPrice,
                dateStr
            );

            if (orderBO.updateOrder(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Order Updated!").show();
                loadAllOrders();
                clearFields();
                loadNextOrderId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        if (selectedOrderId <= 0) {
            new Alert(Alert.AlertType.WARNING, "Please select an order to delete!").show();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this order?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (orderBO.deleteOrder(selectedOrderId)) {
                        new Alert(Alert.AlertType.INFORMATION, "Order Deleted!").show();
                        loadAllOrders();
                        clearFields();
                        loadNextOrderId();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Delete failed!").show();
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
    public void btnResetOnAction(ActionEvent event) {
        clearFields();
        loadNextOrderId();
    }

    @FXML
    public void btnReportOnAction(ActionEvent event) {
        ReportUtil.generateOrderReport();
    }

    private boolean validateFields() {
        if (cmbProduct.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a product!").show();
            return false;
        }
        if (cmbCustomer.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a customer!").show();
            return false;
        }
        if (txtQuantity.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter quantity!").show();
            return false;
        }
        if (dpOrder_Date.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select order date!").show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        if (cmbProduct != null) cmbProduct.setValue(null);
        if (cmbCustomer != null) cmbCustomer.setValue(null);
        if (txtQuantity != null) txtQuantity.clear();
        if (lblUnit_Price != null) lblUnit_Price.setText("LKR 0.00");
        if (lblTotal_Price != null) lblTotal_Price.setText("LKR 0.00");
        if (lblStock_Available != null) lblStock_Available.setText("0");
        if (dpOrder_Date != null) dpOrder_Date.setValue(LocalDate.now());
        if (tblOrder != null) tblOrder.getSelectionModel().clearSelection();
        selectedOrderId = -1;
    }
}


