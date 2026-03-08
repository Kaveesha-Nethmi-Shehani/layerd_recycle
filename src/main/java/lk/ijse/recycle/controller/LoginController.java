package lk.ijse.recycle.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.image.ImageView;
import lk.ijse.recycle.App;
import lk.ijse.recycle.util.Navigation;

public class

LoginController {
    @FXML
    private Label userNameLabel;

    @FXML
    public TextField user_namefeild;

    @FXML
    public Label PasswordLabel;

    @FXML
    public PasswordField passwordfeild;

    @FXML
    public Hyperlink hyperlinkField;

    @FXML
    public RadioButton radioButton;

    @FXML
    public Button btnpw;

    @FXML
    public Button btnlogin;

    @FXML
    public ImageView loginImage;

    @FXML
    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String realUserName = "nethmi";
        String realPassword = "2005";

        String username = user_namefeild.getText();
        String password = passwordfeild.getText();

        System.out.println(username + " - " + password);

        if (username.equals(realUserName) && password.equals(realPassword)) {

            try {
                Navigation.navigate("dashbord");
            }catch (Exception e){
                e.printStackTrace();

            }


        } else {


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Faild");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Username or password is incorrect!");
            alert.showAndWait();

        }
    }
    @FXML
    public void btnuser_name(ActionEvent actionEvent) {
        user_namefeild.requestFocus();
    }

    @FXML
    public void btnpassword(ActionEvent actionEvent) {
        passwordfeild.requestFocus();
    }

    @FXML
    public void passwordhyperlink(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Forgot Password");
        alert.setHeaderText("Please Enter Your Password");
        alert.showAndWait();

    }


    @FXML
    public void btnrememberme(ActionEvent actionEvent) {
        if (radioButton.isSelected()) {
            System.out.println("remember Me  selected");
        }
    }



    @FXML
    public void Btnlogin(ActionEvent actionEvent) throws IOException {
        btnLoginOnAction(actionEvent);
    }
}













