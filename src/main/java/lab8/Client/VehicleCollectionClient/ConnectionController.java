package lab8.Client.VehicleCollectionClient;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lab8.Client.VehicleCollectionClient.Resources.LocalResources;
import lab8.Exceptions.InputException;

public class ConnectionController {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField inputField;
    @FXML
    private Label enterIPAndPort;
    @FXML
    private Button connectButton;
    @FXML
    private Button cancelButton;

    public void updateLabels(){
        enterIPAndPort.setText(LocalResources.rb.getString("enterIpAndPort"));
        connectButton.setText(LocalResources.rb.getString("connectButton"));
        cancelButton.setText(LocalResources.rb.getString("cancelButton"));
    }

    public void cancel(){
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    public void connect(){
        String input = inputField.getText();
        String[] args;
        try {
            args = input.split("[.:]");
            if(args.length < 5) throw new InputException("Not enough arguments");

            ClientConnectionHandler.connect(args);

            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(LocalResources.rb.getString("error"));
            alert.setContentText(LocalResources.rb.getString("unableToConnect")+"!\n" + e);
            alert.showAndWait();
        }
    }
}
