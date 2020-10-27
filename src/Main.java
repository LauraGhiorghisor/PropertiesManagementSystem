import Controller.PropController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Optional;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void closeWindowEvent(WindowEvent event) {
//https://www.programcreek.com/java-api-examples/?class=javafx.scene.control.Alert.AlertType&method=CONFIRMATION
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit?");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            PropController controller = new PropController();
            controller.deleteSession();
            System.exit(0);
        } else {
            event.consume();
        }
    }
//Starts the programme.
    public void start(Stage primaryStage) throws IOException {
        Parent loginPage = FXMLLoader.load(getClass().getResource("/FXMLview/Login.fxml"));
        Scene scene = new Scene(loginPage);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Your Properties Sales System");
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
/*
        Admin reinitialize

        MyFile file = new MyFile("Files/admin.dat");
        Admin admin = new Admin("admin", "admin");
        file.writeTo(admin);
        Admin admin1 = new Admin("admin1", "admin1");
        file.appendTo(admin1);
        Admin admin2 = new Admin("admin2", "admin2");
        file.appendTo(admin2);
*/
    }
}
