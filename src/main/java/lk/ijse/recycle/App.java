package lk.ijse.recycle;

import javafx.application.Application;
import javafx.stage.Stage;
import lk.ijse.recycle.util.Navigation;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Navigation.setStage(stage);

        Navigation.navigate("dashbord");
    }
    public static void main(String[] args) {
        launch(args);
    }
}






