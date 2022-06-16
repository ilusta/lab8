package lab8.Client.VehicleCollectionClient.ConnectionWindow;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lab8.Client.VehicleCollectionClient.Resources.LocalResources;
import lab8.Client.VehicleCollectionClient.VehicleCollectionClient;
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
        System.out.println("Try to connect");
        String input = inputField.getText();
        String[] args;
        try {
            args = input.split("[.:]");
            if(args.length < 5) throw new InputException("Not enough arguments");

            VehicleCollectionClient.connect(args);

            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
        }
        catch (Exception e){
            System.out.println("Error-----------------");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(LocalResources.rb.getString("unableToConnect")+"!");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }
}
