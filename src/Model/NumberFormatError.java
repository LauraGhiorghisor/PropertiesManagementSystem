package Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//Customizes the regular Exception
public class NumberFormatError extends NumberFormatException {

    private NumberFormatException error;

    public NumberFormatError(NumberFormatException er) {
        this.error = er;
    }

    @Override
//    Opens a customized error window.
    public String getMessage() {
        try {
            Parent addProperty = FXMLLoader.load(getClass().getResource("/FXMLview/InputException.fxml"));
            Stage stage = new Stage();
            Scene add = new Scene(addProperty);
            stage.setTitle("Error");
            stage.setScene(add);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

