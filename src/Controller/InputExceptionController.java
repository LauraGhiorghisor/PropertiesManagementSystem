package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InputExceptionController {
    @FXML
    private Button OKBtn;

    public void InputExceptionController() {
    }

    //    closes the window
    public void exit() {
        Stage stage = (Stage) OKBtn.getScene().getWindow();
        stage.close();
    }
}
