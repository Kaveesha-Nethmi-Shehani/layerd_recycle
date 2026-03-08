package lk.ijse.recycle.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {

    private static Stage stage;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void navigate(String fxml) throws Exception {
        String path = "/lk/ijse/recycle/" + fxml + ".fxml";
        java.net.URL resource = Navigation.class.getResource(path);

        if (resource == null) {
            throw new IllegalArgumentException("Cannot find FXML file: " + path);
        }

        Parent root = FXMLLoader.load(resource);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void navigateToPane(AnchorPane pane, String fxml) throws IOException {
        String path = "/lk/ijse/recycle/" + fxml + ".fxml";
        java.net.URL resource = Navigation.class.getResource(path);

        if (resource == null) {
            System.out.println("Path chack: " + path);
            throw new IllegalArgumentException("Cannot find FXML file: " + path);
        }
    try{
        Parent root = FXMLLoader.load(resource);
        pane.getChildren().clear();
        pane.getChildren().add(root);


        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);

    }catch(IOException e){
        System.out.println("Error loading FXML : " + e.getMessage());
        throw e;

    }
    }
}

