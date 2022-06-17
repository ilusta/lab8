package lab8.Client.VehicleCollectionClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import lab8.Commands.Info;
import lab8.Commands.SumOfNumberOfWheels;
import lab8.Essentials.AppVehicle;
import lab8.Client.VehicleCollectionClient.Resources.LocalResources;
import lab8.Commands.Show;
import lab8.Essentials.Reply;
import lab8.Essentials.Request;
import lab8.Essentials.Vehicle.Vehicle;
import lab8.Exceptions.ConnectionException;
import lab8.Essentials.Vehicle.VehicleType;

import java.time.ZonedDateTime;
import java.util.ArrayList;

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
    private MenuItem menuCollectionSumOfWheels;
    @FXML
    private Menu menuHelp;
    @FXML
    private MenuItem menuHelpAbout;

    @FXML
    private TableView<AppVehicle> vehiclesTable;
    @FXML
    private TableColumn<AppVehicle, Long> idColumn;
    @FXML
    private TableColumn<AppVehicle, String> keyColumn;
    @FXML
    private TableColumn<AppVehicle, String> userColumn;
    @FXML
    private TableColumn<AppVehicle, String> nameColumn;
    @FXML
    private TableColumn coordinatesColumn;
    @FXML
    private TableColumn<AppVehicle, Integer> coordinatesXColumn;
    @FXML
    private TableColumn<AppVehicle, Integer> coordinatesYColumn;
    @FXML
    private TableColumn<AppVehicle, String> dateColumn;
    @FXML
    private TableColumn<AppVehicle, Double> enginePowerColumn;
    @FXML
    private TableColumn<AppVehicle, Long> numberOfWheelsColumn;
    @FXML
    private TableColumn<AppVehicle, Double> capacityColumn;
    @FXML
    private TableColumn<AppVehicle, VehicleType> typeColumn;

    @FXML
    protected Label connectionStatusLabel;
    @FXML
    protected Label collectionInfoLabel;
    @FXML
    protected Button addVehicleButton;

    Stage stage;

    ObservableList collection = FXCollections.observableArrayList();

    static protected String user = null;
    static protected String password = null;

    private String collectionInfo = "";


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
        menuCollectionSumOfWheels.setText(LocalResources.rb.getString("menuCollectionSumOfWheels"));
        menuHelp.setText(LocalResources.rb.getString("menuHelp"));
        menuHelpAbout.setText(LocalResources.rb.getString("menuHelpAbout"));

        vehiclesTable.setPlaceholder(new Label(LocalResources.rb.getString("emptyTable")));
        idColumn.setText(LocalResources.rb.getString("idColumn"));
        keyColumn.setText(LocalResources.rb.getString("keyColumn"));
        userColumn.setText(LocalResources.rb.getString("userColumn"));
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
        collectionInfoLabel.setText(LocalResources.rb.getString("collectionInfoLabel")+": "+collectionInfo);
        addVehicleButton.setText(LocalResources.rb.getString("addVehicleButton"));

        updateServerConnection();
    }

    public void initTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<AppVehicle, Long>("id"));
        keyColumn.setCellValueFactory(new PropertyValueFactory<AppVehicle, String>("key"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<AppVehicle, String>("name"));
        userColumn.setCellValueFactory(new PropertyValueFactory<AppVehicle, String>("user"));
        coordinatesXColumn.setCellValueFactory(new PropertyValueFactory<AppVehicle, Integer>("x"));
        coordinatesYColumn.setCellValueFactory(new PropertyValueFactory<AppVehicle, Integer>("y"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<AppVehicle, String>("date"));
        enginePowerColumn.setCellValueFactory(new PropertyValueFactory<AppVehicle, Double>("enginePower"));
        numberOfWheelsColumn.setCellValueFactory(new PropertyValueFactory<AppVehicle, Long>("numberOfWheels"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<AppVehicle, Double>("capacity"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<AppVehicle, VehicleType>("type"));
    }

    public void updateTableContent(ObservableList<AppVehicle> vehicles){
        vehiclesTable.setItems(vehicles);
        vehiclesTable.setEditable(true);
        //vehiclesTable.getSelectionModel().setCellSelectionEnabled(true);
    }

    public void updateServerConnection(){
        boolean isConnected = ClientConnectionHandler.isConnected();
        menuServerLogIn.setDisable(!isConnected);
        menuServerRegister.setDisable(!isConnected);
        menuCollectionInfo.setDisable(!isConnected);
        menuCollectionAdd.setDisable(!isConnected);
        menuCollectionSort.setDisable(!isConnected);
        menuCollectionSumOfWheels.setDisable(!isConnected);

        if(isConnected)
            if (user != null)
                connectionStatusLabel.setText(LocalResources.rb.getString("connectionStatusLabel") + ": " + LocalResources.rb.getString("authorizedAs")+" "+AppController.user);
            else
                connectionStatusLabel.setText(LocalResources.rb.getString("connectionStatusLabel") + ": " + LocalResources.rb.getString("connected"));
        else
            connectionStatusLabel.setText(LocalResources.rb.getString("connectionStatusLabel")+": "+LocalResources.rb.getString("notConnected"));


    }

    public void connectionLostError(){
        VehicleCollectionClient.disconnect();
        collection.clear();
        user = null;
        password = null;
        collectionInfo = "";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(LocalResources.rb.getString("error"));
        alert.setContentText(LocalResources.rb.getString("connectionLost") + "!");
        alert.showAndWait();
        updateServerConnection();
    }

    public void updateCollectionFromServer(){
        if(ClientConnectionHandler.isConnected()) {
            try {
                ClientConnectionHandler.write(new Request(user, password, new Show()));
                Reply reply = ClientConnectionHandler.read();

                if (!reply.isSuccessful()) throw new RuntimeException("Reply is error");

                collection.clear();
                ArrayList<Vehicle> data = (ArrayList<Vehicle>) reply.getData();
                data.stream().map(veh -> new AppVehicle(veh)).forEach(veh -> collection.add(veh));
                updateTableContent(collection);

                ClientConnectionHandler.write(new Request(user, password, new Info()));
                char[] rawInfo = ((String) ClientConnectionHandler.read().getData()).toCharArray();
                for(int i = 0; i < rawInfo.length; i++) if(rawInfo[i] == '\n') rawInfo[i] = ' ';
                StringBuilder info = new StringBuilder();
                info.append(rawInfo);
                collectionInfo = info.toString();

                collectionInfoLabel.setText(LocalResources.rb.getString("collectionInfoLabel")+": "+collectionInfo);

            } catch (Exception e) {
                if(e instanceof ConnectionException) connectionLostError();
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(LocalResources.rb.getString("error"));
                    alert.setContentText(LocalResources.rb.getString("errorWhileReceivingCollection") + "!\n" + e);
                    alert.showAndWait();
                }
            }
        }
    }

    public void login(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogInWindow.fxml"));
            Parent root = loader.load();
            LogInController controller = loader.getController();
            controller.updateLabels();
            stage.setScene(new Scene(root));
            stage.setTitle(LocalResources.rb.getString("logInWindow"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainPain.getScene().getWindow());
            stage.setResizable(false);
            stage.showAndWait();
            updateServerConnection();
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void register(ActionEvent event){

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

            updateServerConnection();
            login();
            updateCollectionFromServer();

        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void getCollectionInformation(ActionEvent event) {
        try {
            ClientConnectionHandler.write(new Request(user, password, new Info()));
            String message = (String) ClientConnectionHandler.read().getData();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(LocalResources.rb.getString("collectionInformationWindow"));
            alert.setContentText(message);
            alert.showAndWait();
        }
        catch (ConnectionException e){
            connectionLostError();
        }
    }

    public void getSumOfWheels(ActionEvent event) {
        try {
            ClientConnectionHandler.write(new Request(user, password, new SumOfNumberOfWheels()));
            Long n = (Long) ClientConnectionHandler.read().getData();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(LocalResources.rb.getString("sumOfWheelsWindow"));
            alert.setContentText(n.toString());
            alert.showAndWait();
        }
        catch (ConnectionException e){
            connectionLostError();
        }
    }

    public void getAppInformation(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LocalResources.rb.getString("aboutAppWindow"));
        alert.setContentText(LocalResources.rb.getString("informationAboutApp"));
        alert.showAndWait();
    }
}
