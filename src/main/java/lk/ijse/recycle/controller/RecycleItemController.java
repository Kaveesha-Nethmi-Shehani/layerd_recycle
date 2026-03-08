package lk.ijse.recycle.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import lk.ijse.recycle.bo.BOFactory;
import lk.ijse.recycle.bo.custom.MaterialBO;
import lk.ijse.recycle.bo.custom.RecycleItemBO;
import lk.ijse.recycle.dto.MaterialDto;
import lk.ijse.recycle.dto.RecycleItemDto;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RecycleItemController implements Initializable {

    @FXML private Label lblItem_Id;
    @FXML private ComboBox<MaterialDto> cmbMaterial;
    @FXML private TextField txtItem_Kg;
    @FXML private Label lblItem_Price;
    @FXML private Label lblPrice_Per_Kg;
    @FXML private DatePicker txtItem_Date;

    @FXML private TableView<RecycleItemDto> tblRecycleItem;
    @FXML private TableColumn<RecycleItemDto, Integer> colItem_Id;
    @FXML private TableColumn<RecycleItemDto, String> colItem_Materials;
    @FXML private TableColumn<RecycleItemDto, Double> colItem_Kg;
    @FXML private TableColumn<RecycleItemDto, Double> colItem_Price;
    @FXML private TableColumn<RecycleItemDto, String> colItem_Date;


    //private final RecycleItemModel model = new RecycleItemModel();
   //private final MaterialModel materialModel = new MaterialModel();

    private int selectedItemId = -1;

//    private final RecycleItemDAOImpl dao = new RecycleItemDAOImpl();
//    private final MaterialDAOImpl materialDAO = new MaterialDAOImpl();

    RecycleItemBO recycleItemBO = (RecycleItemBO)  BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.RecycleItem);
    MaterialBO  materialBO = (MaterialBO)  BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.Material);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        loadTable();
        loadNextItemId();
        loadMaterialsComboBox();
        setupAutoCalculation();

        tblRecycleItem.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                selectedItemId = newVal.getItemId();
                lblItem_Id.setText(String.valueOf(newVal.getItemId()));
                txtItem_Kg.setText(String.valueOf(newVal.getItemKg()));
                lblItem_Price.setText(String.format("LKR %.2f", newVal.getItemPrice()));


                for (MaterialDto m : cmbMaterial.getItems()) {
                    if (m.getMaterialId() == newVal.getMaterialId()) {
                        cmbMaterial.setValue(m);
                        break;
                    }
                }

                if (newVal.getDate() != null && !newVal.getDate().isEmpty()) {
                    try {
                        txtItem_Date.setValue(LocalDate.parse(newVal.getDate().split(" ")[0]));
                    } catch (Exception e) {
                        txtItem_Date.setValue(null);
                    }
                }
            }
        });
    }

    private void loadMaterialsComboBox() {
        if (cmbMaterial == null) return;

        try {

            cmbMaterial.setItems(FXCollections.observableArrayList(recycleItemBO.getAllRecycleItemMaterials()));

            cmbMaterial.setConverter(new StringConverter<MaterialDto>() {
                @Override
                public String toString(MaterialDto m) {
                    if (m == null) return "";
                    return m.getMaterialName() + " (LKR " + m.getPricePerKg() + "/kg)";
                }

                @Override
                public MaterialDto fromString(String string) {
                    return null;
                }
            });
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load materials: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupAutoCalculation() {

        if (txtItem_Kg != null) {
            txtItem_Kg.textProperty().addListener((obs, oldVal, newVal) -> calculatePrice());
        }


        if (cmbMaterial != null) {
            cmbMaterial.valueProperty().addListener((obs, oldVal, newVal) -> {
                updatePricePerKgLabel();
                calculatePrice();
            });
        }
    }

    private void updatePricePerKgLabel() {
        if (lblPrice_Per_Kg == null) return;

        MaterialDto selected = cmbMaterial.getValue();
        if (selected != null) {
            lblPrice_Per_Kg.setText(String.format("LKR %.2f/kg", selected.getPricePerKg()));
        } else {
            lblPrice_Per_Kg.setText("Select material");
        }
    }

    private void calculatePrice() {
        if (cmbMaterial == null || txtItem_Kg == null || lblItem_Price == null) return;

        MaterialDto material = cmbMaterial.getValue();
        String kgText = txtItem_Kg.getText().trim();

        if (material == null || kgText.isEmpty()) {
            lblItem_Price.setText("LKR 0.00");
            return;
        }

        try {
            double kg = Double.parseDouble(kgText);
            double totalPrice = kg * material.getPricePerKg();
            lblItem_Price.setText(String.format("LKR %.2f", totalPrice));
        } catch (NumberFormatException e) {
            lblItem_Price.setText("Invalid KG");
        }
    }

    private double getCalculatedPrice() {
        MaterialDto material = cmbMaterial.getValue();
        String kgText = txtItem_Kg.getText().trim();

        if (material == null || kgText.isEmpty()) return 0.0;

        try {
            double kg = Double.parseDouble(kgText);
            return kg * material.getPricePerKg();
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void setCellValueFactory() {
        if (colItem_Id == null) return;
        colItem_Id.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItem_Materials.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colItem_Kg.setCellValueFactory(new PropertyValueFactory<>("itemKg"));
        colItem_Price.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        colItem_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadNextItemId() {
        try {
            int nextId = recycleItemBO.getNextRecycleItemId();
            lblItem_Id.setText(String.valueOf(nextId));
            selectedItemId = -1;
        } catch (SQLException e) {
            lblItem_Id.setText("N/A");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTable() {
        if (tblRecycleItem == null) return;
        try {
            tblRecycleItem.setItems(FXCollections.observableArrayList(recycleItemBO.getAllRecycleItem()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load recycle items: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            if (cmbMaterial.getValue() == null || txtItem_Kg.getText().isEmpty() || txtItem_Date.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
                return;
            }

            String dateStr = txtItem_Date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            MaterialDto material = cmbMaterial.getValue();
            double kg = Double.parseDouble(txtItem_Kg.getText());
            double calculatedPrice = getCalculatedPrice();

            RecycleItemDto dto = new RecycleItemDto(
                    0,
                    material.getMaterialId(),
                    kg,
                    calculatedPrice,
                    dateStr
            );

            if (recycleItemBO.saveRecycleItem(dto)) {
                new Alert(Alert.AlertType.INFORMATION,
                        String.format("Item Saved!\nMaterial: %s\nWeight: %.2f kg\nPrice Paid: LKR %.2f",
                                material.getMaterialName(), kg, calculatedPrice)).show();
                loadTable();
                clearFields();
                loadNextItemId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save Failed!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input! KG must be a number!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (selectedItemId <= 0) {
            new Alert(Alert.AlertType.WARNING, "Please select an item from the table to update!").show();
            return;
        }

        try {
            String dateStr = txtItem_Date.getValue() != null ?
                    txtItem_Date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
            MaterialDto material = cmbMaterial.getValue();
            double kg = Double.parseDouble(txtItem_Kg.getText());
            double calculatedPrice = getCalculatedPrice();

            RecycleItemDto dto = new RecycleItemDto(
                    selectedItemId,
                    material.getMaterialId(),
                    kg,
                    calculatedPrice,
                    dateStr
            );

            if (recycleItemBO.updateRecycleItem(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
                loadTable();
                clearFields();
                loadNextItemId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update Failed!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input! KG must be a number!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (selectedItemId <= 0) {
            new Alert(Alert.AlertType.WARNING, "Please select an item from the table to delete!").show();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (recycleItemBO.deleteRecycleItem(selectedItemId)) {
                        new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
                        loadTable();
                        clearFields();
                        loadNextItemId();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Delete Failed!").show();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        });
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        MaterialDto material = cmbMaterial.getValue();
        if (material == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a material to search!").show();
            return;
        }

        for (RecycleItemDto dto : tblRecycleItem.getItems()) {
            if (dto.getMaterialId() == material.getMaterialId()) {
                tblRecycleItem.getSelectionModel().select(dto);
                return;
            }
        }
        new Alert(Alert.AlertType.INFORMATION, "No items found for material: " + material.getMaterialName()).show();
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        clearFields();
        loadNextItemId();
    }

    @FXML
    void btnAddMaterialOnAction(ActionEvent event) {
        showMaterialManagementDialog();
    }


    private void showMaterialManagementDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Material Management");
        dialog.setHeaderText("Add, Edit, or Delete Materials");


        VBox content = new VBox(10);
        content.setPadding(new Insets(20));


        ListView<MaterialDto> listView = new ListView<>();
        listView.setPrefHeight(200);
        refreshMaterialList(listView);


        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        TextField txtMaterialName = new TextField();
        txtMaterialName.setPromptText("Material Name");
        TextField txtPricePerKg = new TextField();
        txtPricePerKg.setPromptText("Price per KG");

        inputGrid.add(new Label("Material Name:"), 0, 0);
        inputGrid.add(txtMaterialName, 1, 0);
        inputGrid.add(new Label("Price/KG (LKR):"), 0, 1);
        inputGrid.add(txtPricePerKg, 1, 1);


        HBox buttonBox = new HBox(10);
        Button btnAdd = new Button("Add New");
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");

        btnAdd.setStyle("-fx-background-color: #0E5930; -fx-text-fill: white;");
        btnUpdate.setStyle("-fx-background-color: #639354; -fx-text-fill: white;");
        btnDelete.setStyle("-fx-background-color: #CC0000; -fx-text-fill: white;");

        buttonBox.getChildren().addAll(btnAdd, btnUpdate, btnDelete);


        listView.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                txtMaterialName.setText(newVal.getMaterialName());
                txtPricePerKg.setText(String.valueOf(newVal.getPricePerKg()));
            }
        });


        btnAdd.setOnAction(e -> {
            if (txtMaterialName.getText().trim().isEmpty() || txtPricePerKg.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
                return;
            }
            try {
                MaterialDto dto = new MaterialDto(0, txtMaterialName.getText().trim(),
                        Double.parseDouble(txtPricePerKg.getText().trim()));
                if (materialBO.saveMaterial(dto)) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Added!").show();
                    refreshMaterialList(listView);
                    loadMaterialsComboBox();
                    txtMaterialName.clear();
                    txtPricePerKg.clear();
                }
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Invalid price!").show();
            } catch (SQLException | ClassNotFoundException ex) {
                new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).show();
            }
        });


        btnUpdate.setOnAction(e -> {
            MaterialDto selected = listView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a material to update!").show();
                return;
            }
            try {
                MaterialDto dto = new MaterialDto(selected.getMaterialId(),
                        txtMaterialName.getText().trim(),
                        Double.parseDouble(txtPricePerKg.getText().trim()));
                if (materialBO.updateMaterial(dto)) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Updated!").show();
                    refreshMaterialList(listView);
                    loadMaterialsComboBox();
                }
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Invalid price!").show();
            } catch (SQLException | ClassNotFoundException ex) {
                new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).show();
            }
        });


        btnDelete.setOnAction(e -> {
            MaterialDto selected = listView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a material to delete!").show();
                return;
            }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete '" + selected.getMaterialName() + "'?\n" +
                            "This will fail if items are using this material.");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        if (materialBO.deleteMaterial(selected.getMaterialId())) {
                            new Alert(Alert.AlertType.INFORMATION, "Material Deleted!").show();
                            refreshMaterialList(listView);
                            loadMaterialsComboBox();
                            txtMaterialName.clear();
                            txtPricePerKg.clear();
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        new Alert(Alert.AlertType.ERROR, "Cannot delete! Material is in use.").show();
                    }
                }
            });
        });

        content.getChildren().addAll(
                new Label("Existing Materials:"),
                listView,
                new Separator(),
                inputGrid,
                buttonBox
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setPrefWidth(400);

        dialog.showAndWait();
    }

    private void refreshMaterialList(ListView<MaterialDto> listView) {
        try {
            listView.setItems(FXCollections.observableArrayList(materialBO.getAllMaterial()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        if (cmbMaterial != null) cmbMaterial.setValue(null);
        if (txtItem_Kg != null) txtItem_Kg.clear();
        if (lblItem_Price != null) lblItem_Price.setText("LKR 0.00");
        if (lblPrice_Per_Kg != null) lblPrice_Per_Kg.setText("Select material");
        if (txtItem_Date != null) txtItem_Date.setValue(null);
        if (tblRecycleItem != null) tblRecycleItem.getSelectionModel().clearSelection();
        selectedItemId = -1;
    }
    
    @FXML
    void btnReportOnAction(ActionEvent event) {
        try {
            String reportPath = "src/main/resources/lk/ijse/recycle/report/RecycleItem_Analysis.jrxml";
            net.sf.jasperreports.engine.JasperReport jasperReport = net.sf.jasperreports.engine.JasperCompileManager.compileReport(reportPath);
            java.sql.Connection conn = lk.ijse.recycle.db.DBConnection.getDbConnection().getConnection();
            net.sf.jasperreports.engine.JasperPrint jasperPrint = net.sf.jasperreports.engine.JasperFillManager.fillReport(jasperReport, null, conn);
            net.sf.jasperreports.view.JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate analysis report: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }
}





