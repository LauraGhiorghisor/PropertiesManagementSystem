package Controller;

import Model.Admin;
import Model.Branch;
import Model.MyFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    @FXML
    private TextField userField, passwordField;
    @FXML
    private javafx.scene.control.Button loginButton;
    @FXML
    private HBox loginFeedback;
    @FXML
    private Text loginFeedbackText;

    public LoginController() {
    }

    public void loginEvent(ActionEvent event) {
        login();

    }

    //allows login by pressing enter
    public void loginKey(KeyEvent k) {
        if (k.getCode() == KeyCode.ENTER) login();
    }

    //checks for user credentials
    public void login() {
        try {
            Parent homePage = null;
            Boolean found = false;
            MyFile file = new MyFile("Files/admin.dat");
            ArrayList<Admin> admins = file.readFrom();
            Admin a = new Admin();
//Looks for credentials in the admin file
            for (int i = 0; i < admins.size(); i++)
                if (userField.getText().equals(admins.get(i).getUsername()) && passwordField.getText().equals(admins.get(i).getPassword())) {
                    found = true;
                    a.setUsername(userField.getText());
                    a.setPassword(passwordField.getText());
                }
//If credentials are found, a session is written
            if (found) {
                MyFile sesh = new MyFile("Files/session.dat");
                sesh.writeTo(a);
                homePage = FXMLLoader.load(getClass().getResource("/FXMLview/AdminHome.fxml"));
                loginButton.getScene().setRoot(homePage);
            }
// If no admin credentials were found, looks for them in the branches
            else {
                MyFile bf = new MyFile("Files/branches.dat");
                ArrayList<Branch> branchlist = new ArrayList<Branch>();
                branchlist = bf.readFrom();

                for (int i = 0; i < branchlist.size(); i++) {
                    if (branchlist.get(i).getUsername().equals(userField.getText()) && branchlist.get(i).getPassword().equals(passwordField.getText())) {
                        MyFile sesh1 = new MyFile("Files/session.dat");
                        sesh1.writeTo(branchlist.get(i));
                        homePage = FXMLLoader.load(getClass().getResource("/FXMLview/Home.fxml"));
                        loginButton.getScene().setRoot(homePage);
                        found = true;
                    }
                }
            }
// If no credentials are found, on-screen feedback is displayed to alert the user
            if (found == false) {
                loginFeedback.setVisible(true);
                loginFeedbackText.setVisible(true);
            } else {
                loginFeedback.setVisible(false);
                loginFeedbackText.setVisible(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
