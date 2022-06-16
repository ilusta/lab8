package lab8.Client.VehicleCollectionClient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import lab8.Client.VehicleCollectionClient.ConnectionWindow.ConnectionController;
import lab8.Client.VehicleCollectionClient.Resources.LocalResources;

public class AppController {

    @FXML
    private BorderPane mainPain;
    @FXML
    private Menu menuFile;
    @FXML
    private Menu menuFileChangeLang;
    @FXML
    private MenuItem menuFileExit;
    @FXML
    private Menu menuServer;
    @FXML
    private MenuItem menuServerConnect;
    @FXML
    private MenuItem menuServerLogIn;
    @FXML
    private MenuItem menuServerRegister;
    @FXML
    private Menu menuCollection;
    @FXML
    private MenuItem menuCollectionInfo;
    @FXML
    private MenuItem menuCollectionAdd;
    @FXML
    private Menu menuCollectionSort;
    @FXML
    private MenuItem menuCollectionSortByType;
    @FXML
    private MenuItem menuCollectionSortByName;
    @FXML
    private Menu menuHelp;
    @FXML
    private MenuItem menuHelpAbout;

    @FXML
    private TableView vehiclesTable;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn keyColumn;
    @FXML
    private TableColumn userColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn coordinatesColumn;
    @FXML
    private TableColumn coordinatesXColumn;
    @FXML
    private TableColumn coordinatesYColumn;
    @FXML
    private TableColumn dateColumn;
    @FXML
    private TableColumn enginePowerColumn;
    @FXML
    private TableColumn numberOfWheelsColumn;
    @FXML
    private TableColumn capacityColumn;
    @FXML
    private TableColumn typeColumn;

    @FXML
    private Label connectionStatusLabel;
    @FXML
    private Label collectionInfoLabel;
    @FXML
    private Button addVehicleButton;

    Stage stage;

    public void exit(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LocalResources.rb.getString("attention")+"!");
        alert.setContentText(LocalResources.rb.getString("closeApp")+"?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) mainPain.getScene().getWindow();
            stage.close();
        }
    }

    public void changeLanguageToEn(){
        LocalResources.changeResources("en");
        updateLabels();
    }

    public void changeLanguageToRu(){
        LocalResources.changeResources("ru");
        updateLabels();
    }

    public void changeLanguageToTr(){
        LocalResources.changeResources("tr");
        updateLabels();
    }

    public void changeLanguageToDa(){
        LocalResources.changeResources("da");
        updateLabels();
    }

    public void changeLanguageToEsMe(){
        LocalResources.changeResources("es", "Me");
        updateLabels();
    }

    public void updateLabels(){
        menuFile.setText(LocalResources.rb.getString("menuFile"));
        menuFileChangeLang.setText(LocalResources.rb.getString("menuFileChangeLang"));
        menuFileExit.setText(LocalResources.rb.getString("menuFileExit"));
        menuServer.setText(LocalResources.rb.getString("menuServer"));
        menuServerConnect.setText(LocalResources.rb.getString("menuServerConnect"));
        menuServerLogIn.setText(LocalResources.rb.getString("menuServerLogIn"));
        menuServerRegister.setText(LocalResources.rb.getString("menuServerRegister"));
        menuCollection.setText(LocalResources.rb.getString("menuCollection"));
        menuCollectionInfo.setText(LocalResources.rb.getString("menuCollectionInfo"));
        menuCollectionAdd.setText(LocalResources.rb.getString("menuCollectionAdd"));
        menuCollectionSort.setText(LocalResources.rb.getString("menuCollectionSort"));
        menuCollectionSortByType.setText(LocalResources.rb.getString("menuCollectionSortByType"));
        menuCollectionSortByName.setText(LocalResources.rb.getString("menuCollectionSortByName"));
        menuHelp.setText(LocalResources.rb.getString("menuHelp"));
        menuHelpAbout.setText(LocalResources.rb.getString("menuHelpAbout"));

        vehiclesTable.setPlaceholder(new Label(LocalResources.rb.getString("emptyTable")));
        idColumn.setText(LocalResources.rb.getString("idColumn"));
        keyColumn.setText(LocalResources.rb.getString("keyColumn"));
        userColumn.setText(LocalResources.rb.getString("keyColumn"));
        nameColumn.setText(LocalResources.rb.getString("nameColumn"));
        coordinatesColumn.setText(LocalResources.rb.getString("coordinatesColumn"));
        coordinatesXColumn.setText(LocalResources.rb.getString("coordinatesXColumn"));
        coordinatesYColumn.setText(LocalResources.rb.getString("coordinatesYColumn"));
        dateColumn.setText(LocalResources.rb.getString("dateColumn"));
        enginePowerColumn.setText(LocalResources.rb.getString("enginePowerColumn"));
        numberOfWheelsColumn.setText(LocalResources.rb.getString("numberOfWheelsColumn"));
        capacityColumn.setText(LocalResources.rb.getString("capacityColumn"));
        typeColumn.setText(LocalResources.rb.getString("typeColumn"));

        connectionStatusLabel.setText(LocalResources.rb.getString("connectionStatusLabel")+":");
        collectionInfoLabel.setText(LocalResources.rb.getString("collectionInfoLabel")+":");
        addVehicleButton.setText(LocalResources.rb.getString("addVehicleButton"));
    }

    public void connect(ActionEvent event){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ServerConnectionWindow.fxml"));
            Parent root = loader.load();
            ConnectionController controller = loader.getController();
            controller.updateLabels();
            stage.setScene(new Scene(root));
            stage.setTitle(LocalResources.rb.getString("connectToServerWindow"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainPain.getScene().getWindow());
            stage.setResizable(false);
            stage.showAndWait();

            if(ClientConnectionHandler.isConnected())
                connectionStatusLabel.setText(LocalResources.rb.getString("connectionStatusLabel")+": "+LocalResources.rb.getString("connected"));
            else
                connectionStatusLabel.setText(LocalResources.rb.getString("connectionStatusLabel")+": "+LocalResources.rb.getString("notConnected"));
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
    }
}
