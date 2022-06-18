package lab8.Client.VehicleCollectionClient;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import lab8.Client.VehicleCollectionClient.Resources.LocalResources;
import lab8.Commands.Command;
import lab8.Commands.Insert;
import lab8.Commands.RegisterUser;
import lab8.Commands.Update;
import lab8.Essentials.AppVehicle;
import lab8.Essentials.Reply;
import lab8.Essentials.Request;
import lab8.Essentials.Vehicle.VehicleType;
import lab8.Essentials.Vehicle.VehicleTypeStringConverter;

import java.time.ZonedDateTime;

public class AddController {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField keyField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private TextField powerField;
    @FXML
    private TextField wheelsField;
    @FXML
    private TextField capacityField;
    @FXML
    private TextField typeField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;

    public void updateLabels(){
        //usernameField.setPromptText(LocalResources.rb.getString("user"));
        //passwordField.setPromptText(LocalResources.rb.getString("password"));
        //loginButton.setText(LocalResources.rb.getString("loginButton"));
        cancelButton.setText(LocalResources.rb.getString("cancelButton"));
    }

    public void cancel(){
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    public void add(){

        try{
            Long id = 1234L;
            String key = keyField.getText();
            if(key == null || key.equals("")) throw new RuntimeException(LocalResources.rb.getString("canNotBeNullOrEmpty"));
            String name = nameField.getText();
            if(name == null || name.equals("")) throw new RuntimeException(LocalResources.rb.getString("canNotBeNullOrEmpty"));
            Integer x = new IntegerStringConverter().fromString(xField.getText());
            if(x == null) throw new RuntimeException(LocalResources.rb.getString("canNotBeNull"));
            Integer y = new IntegerStringConverter().fromString(yField.getText());
            if(y == null) throw new RuntimeException(LocalResources.rb.getString("canNotBeNull"));
            ZonedDateTime creationDate = ZonedDateTime.now();
            Double enginePower = new DoubleStringConverter().fromString(powerField.getText());
            if(enginePower != null && enginePower <= 0) throw new RuntimeException(LocalResources.rb.getString("canNotBeNegative"));
            Long numberOfWheels = new LongStringConverter().fromString(wheelsField.getText());
            if(numberOfWheels != null && numberOfWheels <= 0) throw new RuntimeException(LocalResources.rb.getString("canNotBeNegative"));
            Double capacity = new DoubleStringConverter().fromString(capacityField.getText());
            if(capacity == null || capacity <= 0) throw new RuntimeException(LocalResources.rb.getString("canNotBeNullOrNegative"));
            VehicleType type = new VehicleTypeStringConverter().fromString(typeField.getText());
            if(type == null) throw new RuntimeException(LocalResources.rb.getString("mustBeOnOfTypes"));

            Insert insert = new Insert();
            insert.addVehicle(new AppVehicle(id, key,name, AppController.user, x, y, creationDate, enginePower, numberOfWheels, capacity, type));
            ClientConnectionHandler.write(new Request(AppController.user, AppController.password, insert));
            Reply r = ClientConnectionHandler.read();

            if(!r.isSuccessful()) {
                throw new RuntimeException(r.getMessage());
            }

            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();

            //updateCollectionFromServer();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(LocalResources.rb.getString("error"));
            alert.setContentText(LocalResources.rb.getString("wrongInput")+":\n" + e);
            alert.showAndWait();
        }



    }
}
