package lab8.Client.VehicleCollectionClient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lab8.Client.VehicleCollectionClient.Resources.LocalResources;
import lab8.Commands.LogIn;
import lab8.Essentials.Reply;
import lab8.Essentials.Request;

import java.awt.event.ActionEvent;

public class LogInController {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label user;
    @FXML
    private Label password;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;

    public void updateLabels(){
        usernameField.setPromptText(LocalResources.rb.getString("user"));
        passwordField.setPromptText(LocalResources.rb.getString("password"));
        user.setText(LocalResources.rb.getString("user"));
        password.setText(LocalResources.rb.getString("password"));
        loginButton.setText(LocalResources.rb.getString("loginButton"));
        cancelButton.setText(LocalResources.rb.getString("cancelButton"));

        usernameField.setText("ilust");
        passwordField.setText("12345678");
    }

    public void usernameEnter(){
        passwordField.requestFocus();
    }

    public void cancel(){
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    public void login(){
        AppController.user = usernameField.getText();
        AppController.password = passwordField.getText();
        try {
            ClientConnectionHandler.write(new Request(AppController.user, AppController.password, new LogIn()));
            Reply reply = ClientConnectionHandler.read();
            if(!reply.isSuccessful()) throw new RuntimeException(reply.getMessage());

            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(LocalResources.rb.getString("error"));
            alert.setContentText(LocalResources.rb.getString("unableToLogIn")+":\n" + e);
            AppController.user = null;
            AppController.password = null;
            alert.showAndWait();
        }
    }
}
