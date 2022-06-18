package lab8.Client.VehicleCollectionClient;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import lab8.Commands.*;
import lab8.Essentials.AppVehicle;
import lab8.Client.VehicleCollectionClient.Resources.LocalResources;
import lab8.Essentials.Reply;
import lab8.Essentials.Request;
import lab8.Essentials.Vehicle.Vehicle;
import lab8.Essentials.Vehicle.VehicleTypeStringConverter;
import lab8.Exceptions.ConnectionException;
import lab8.Essentials.Vehicle.VehicleType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
    private TextField findField;
    @FXML
    private SplitMenuButton findSelector;
    @FXML
    private MenuItem filterSelectorNone;
    @FXML
    private MenuItem filterSelectorID;
    @FXML
    private MenuItem filterSelectorName;
    @FXML
    private MenuItem filterSelectorKey;

    @FXML
    protected Label connectionStatusLabel;
    @FXML
    protected Label tableInfoLable;

    @FXML
    private ScrollPane mapScrollPane;
    @FXML
    private AnchorPane mapPane;
    @FXML
    private Label mapLabel;


    private Stage stage;
    private ObservableList<AppVehicle> collection = FXCollections.observableArrayList();
    private ArrayList<Vehicle> prevData = new ArrayList<>();

    static protected String user = null;
    static protected String password = null;

    private String selectedFilterColumn = null;

    protected ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(0);
    private ScheduledFuture<?> updateResult;


    public void exit(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LocalResources.rb.getString("attention")+"!");
        alert.setContentText(LocalResources.rb.getString("closeApp")+"?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            if(updateResult != null) updateResult.cancel(true);
            scheduler.shutdown();
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
        tableInfoLable.setText(LocalResources.rb.getString("tableInfoLabel"));
        mapLabel.setText(LocalResources.rb.getString("mapLabelHint"));

        findField.setPromptText(LocalResources.rb.getString("find"));
        findSelector.setText(LocalResources.rb.getString("in"));
        filterSelectorID.setText(LocalResources.rb.getString("idColumnFilter"));
        filterSelectorName.setText(LocalResources.rb.getString("nameColumnFilter"));
        filterSelectorKey.setText(LocalResources.rb.getString("keyColumnFilter"));

        updateServerConnection();
        updateTableContent(collection);
    }

    public void delete(){
        int i = vehiclesTable.getSelectionModel().getFocusedIndex();
        String vehicleUser = userColumn.getCellData(i);

        if(Objects.equals(user, vehicleUser)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(LocalResources.rb.getString("attention") + "!");
            alert.setContentText(LocalResources.rb.getString("deleteVehicle") + "?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                String key = keyColumn.getCellData(i);
                try{
                    ClientConnectionHandler.write(new Request(AppController.user, AppController.password, new RemoveKey().build(new String[]{"", key})));
                    Reply r = ClientConnectionHandler.read();
                    if(!r.isSuccessful()) throw new RuntimeException(r.getMessage());
                }
                catch (Exception e){
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle(LocalResources.rb.getString("Error") + "!");
                    alert2.setContentText(e.getMessage());
                    alert2.showAndWait();
                }
                updateServerConnection();
                updateCollectionFromServer();
            }
        }
    }

    public void addVehicle(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addWindow.fxml"));
            Parent root = loader.load();
            AddController controller = loader.getController();
            controller.updateLabels();
            stage.setScene(new Scene(root));
            stage.setTitle(LocalResources.rb.getString("addWindow"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainPain.getScene().getWindow());
            stage.setResizable(false);
            stage.showAndWait();
            updateServerConnection();
            updateCollectionFromServer();
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void updateVehicleAtServer(AppVehicle vehicle){
        try {
            Update update = new Update();
            update.addVehicle(vehicle);
            ClientConnectionHandler.write(new Request(user, password, update));
            Reply r = ClientConnectionHandler.read();
            if(!r.isSuccessful()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(r.getMessage());
                alert.showAndWait();
            }

            updateCollectionFromServer();
        }catch (Exception e){}
    }


    public void initTable(){
        vehiclesTable.setEditable(true);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            AppVehicle vehicle = event.getRowValue();
            String name = event.getNewValue();
            if(event.getRowValue().getUser().equals(user)) {
                if (name == null || name.equals("")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(LocalResources.rb.getString("wrongInput"));
                    alert.setContentText(LocalResources.rb.getString("canNotBeNullOrEmpty"));
                    alert.showAndWait();
                }
                else{
                    vehicle.setName(name);
                    updateVehicleAtServer(vehicle);
                }
            }
            vehiclesTable.refresh();
        });

        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));

        coordinatesXColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        coordinatesXColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        coordinatesXColumn.setOnEditCommit(event -> {
            AppVehicle vehicle = event.getRowValue();
            Integer x = event.getNewValue();
            if(event.getRowValue().getUser().equals(user)) {
                if (x == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(LocalResources.rb.getString("wrongInput"));
                    alert.setContentText(LocalResources.rb.getString("canNotBeNull"));
                    alert.showAndWait();
                }
                else{
                    vehicle.setX(x);
                    updateVehicleAtServer(vehicle);
                }
            }
            vehiclesTable.refresh();
        });

        coordinatesYColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        coordinatesYColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        coordinatesYColumn.setOnEditCommit(event -> {
            AppVehicle vehicle = event.getRowValue();
            Integer y = event.getNewValue();
            if(event.getRowValue().getUser().equals(user)) {
                if (y == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(LocalResources.rb.getString("wrongInput"));
                    alert.setContentText(LocalResources.rb.getString("canNotBeNull"));
                    alert.showAndWait();
                }
                else {
                    vehicle.setY(y);
                    updateVehicleAtServer(vehicle);
                }
            }
            vehiclesTable.refresh();
        });

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        enginePowerColumn.setCellValueFactory(new PropertyValueFactory<>("enginePower"));
        enginePowerColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        enginePowerColumn.setOnEditCommit(event -> {
            AppVehicle vehicle = event.getRowValue();
            Double power = event.getNewValue();
            if(event.getRowValue().getUser().equals(user)) {
                if (power <= 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(LocalResources.rb.getString("wrongInput"));
                    alert.setContentText(LocalResources.rb.getString("canNotBeNegative"));
                    alert.showAndWait();
                }
                else {
                    vehicle.setEnginePower(power);
                    updateVehicleAtServer(vehicle);
                }
            }
            vehiclesTable.refresh();
        });

        numberOfWheelsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfWheels"));
        numberOfWheelsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        numberOfWheelsColumn.setOnEditCommit(event -> {
            AppVehicle vehicle = event.getRowValue();
            Long number = event.getNewValue();
            if(event.getRowValue().getUser().equals(user)) {
                if (number <= 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(LocalResources.rb.getString("wrongInput"));
                    alert.setContentText(LocalResources.rb.getString("canNotBeNegative"));
                    alert.showAndWait();
                }
                else {
                    vehicle.setNumberOfWheels(number);
                    updateVehicleAtServer(vehicle);
                }
            }
            vehiclesTable.refresh();
        });

        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        capacityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        capacityColumn.setOnEditCommit(event -> {
            AppVehicle vehicle = event.getRowValue();
            Double capacity = event.getNewValue();
            if(event.getRowValue().getUser().equals(user)) {
                if (capacity <= 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(LocalResources.rb.getString("wrongInput"));
                    alert.setContentText(LocalResources.rb.getString("canNotBeNullOrNegative"));
                    alert.showAndWait();
                }
                else {
                    vehicle.setCapacity(capacity);
                    updateVehicleAtServer(vehicle);
                }
            }
            vehiclesTable.refresh();
        });

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new VehicleTypeStringConverter()));
        typeColumn.setOnEditCommit(event -> {
            AppVehicle vehicle = event.getRowValue();
            VehicleType type = event.getNewValue();
            if(event.getRowValue().getUser().equals(user)) {
                if (type == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(LocalResources.rb.getString("wrongInput"));
                    alert.setContentText(LocalResources.rb.getString("mustBeOnOfTypes"));
                    alert.showAndWait();
                }
                else {
                    vehicle.setType(type);
                    updateVehicleAtServer(vehicle);
                }
            }
            vehiclesTable.refresh();
        });
    }

    public void updateTableContent(ObservableList<AppVehicle> vehicles){
        vehiclesTable.setItems(vehicles);
        tableInfoLable.setText(LocalResources.rb.getString("tableInfoShowing")+
                " "+vehicles.size()+" "+LocalResources.rb.getString("tableInfoVehiclesFrom")+
                " "+collection.size()+" "+LocalResources.rb.getString("tableInfoInCollection"));
        vehiclesTable.refresh();
    }

    public void updateMap(ObservableList<AppVehicle> vehicles){
        mapPane.getChildren().clear();

        Integer xMax = vehicles.stream().max(Comparator.comparing(AppVehicle::getX)).get().getX();
        Integer xMin = vehicles.stream().min(Comparator.comparing(AppVehicle::getX)).get().getX();
        Integer yMax = vehicles.stream().max(Comparator.comparing(AppVehicle::getY)).get().getY();
        Integer yMin = vehicles.stream().min(Comparator.comparing(AppVehicle::getY)).get().getY();
        int maxCoord = Math.max(Math.max(Math.abs(xMax), Math.abs(xMin)), Math.max(Math.abs(yMax), Math.abs(yMin)));

        for(AppVehicle vehicle : vehicles){
            ScaleTransition scale = new ScaleTransition();
            Node symbol = mapSymbol(vehicle, (int) mapPane.getWidth(), (int) mapPane.getHeight(), maxCoord);
            scale.setNode(symbol);
            scale.setDuration(Duration.millis(750));
            scale.setFromX(0);
            scale.setFromY(0);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();
            mapPane.getChildren().add(symbol);

            symbol.setOnMouseEntered(event -> {
                scale.setFromX(1);
                scale.setFromY(1);
                scale.setToX(1.2);
                scale.setToY(1.2);
                scale.setDuration(Duration.millis(200));
                scale.play();
                mapLabel.setText(LocalResources.rb.getString("mapLabelVehicleID")+" "+vehicle.getId()+
                        ", "+LocalResources.rb.getString("mapLabelVehicleKey")+" "+vehicle.getKey()+
                        ", "+LocalResources.rb.getString("mapLabelVehicleName")+" "+vehicle.getName());
            });

            symbol.setOnMouseExited(event -> {
                scale.setFromX(1.2);
                scale.setFromY(1.2);
                scale.setToX(1);
                scale.setToY(1);
                scale.setDuration(Duration.millis(200));
                scale.play();
                mapLabel.setText(LocalResources.rb.getString("mapLabelHint"));
            });
        }
    }


    private Canvas mapSymbol(AppVehicle vehicle, int width, int height, int maxCoord){
        int canvasSize = 34;
        int cX = width/2;
        int cY = height/2;
        int maxPixels = Math.max((width-canvasSize)/2, (height-canvasSize)/2);
        double coordToPixel = (double)maxPixels / (double)maxCoord;

        Canvas canvas = new Canvas(34, 34);
        canvas.setTranslateX(cX + vehicle.getX()*coordToPixel);
        canvas.setTranslateY(cY - vehicle.getY()*coordToPixel);

        byte[] user = vehicle.getUser().getBytes();
        int colorHash = 0;
        for(byte b : user) colorHash ^= (b + 127);
        double r = (Math.abs(colorHash)%112)/112.0;
        double g = (Math.abs(colorHash)%67)/67.0;
        double b = (Math.abs(colorHash)%215)/215.0;
        Color color = new Color(r, g, b, 1);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fill();
        gc.setStroke(color);
        gc.setFill(Color.WHITE);

        if (vehicle.getType() == VehicleType.BICYCLE) {
            gc.strokeOval(2, 10, 10, 10);
            gc.strokeOval(22, 10, 10, 10);
            gc.strokeLine(27, 15, 17, 15);
            gc.strokeLine(17, 15, 9, 6);
            gc.strokeLine(7, 15, 9, 6);
            gc.strokeLine(17, 15, 19, 6);
            gc.strokeLine(27, 15, 18, 8);
            gc.strokeLine(9, 6, 18, 8);
        }
        else if (vehicle.getType() == VehicleType.BOAT) {
            gc.strokeLine(2, 17, 32, 17);
            gc.strokeLine(17, 17, 17, 2);
            gc.strokeLine(17, 2, 25, 14);
            gc.strokeLine(17, 14, 25, 14);
            gc.beginPath();
            gc.moveTo(2, 17);
            gc.bezierCurveTo(7, 28, 27, 28, 32, 17);
            gc.stroke();
            gc.closePath();
        }
        else if (vehicle.getType() == VehicleType.HOVERBOARD) {
            gc.strokeLine(8, 18, 26, 18);
            gc.beginPath();
            gc.moveTo(0, 0);
            gc.strokeArc(4, 8, 10, 10, 270, -60, ArcType.OPEN);
            gc.strokeArc(22, 8, 10, 10, 270, 60, ArcType.OPEN);
            gc.closePath();
        }
        else if (vehicle.getType() == VehicleType.HELICOPTER) {
            gc.fillOval(15, 12, 17, 14);
            gc.strokeOval(15, 12, 17, 14);
            gc.strokeOval(2, 14, 10, 10);
            gc.strokeLine(15,19, 7, 19);
            gc.strokeLine(23, 12, 23, 9);
            gc.strokeLine(14, 9, 33, 9);
        }
        else if (vehicle.getType() == VehicleType.DRONE) {
            gc.fillOval(12, 12, 10, 10);
            gc.strokeOval(12, 12, 10, 10);
            gc.strokeLine(6,17, 12, 17);
            gc.strokeLine(22,17, 28, 17);
            gc.strokeLine(6,17, 6, 13);
            gc.strokeLine(28,17, 28, 13);
            gc.strokeLine(1,13, 11, 13);
            gc.strokeLine(23,13, 33, 13);
        }
        else {
            gc.fillOval(7, 7, 20, 20);
            gc.strokeOval(7, 7, 20, 20);
        }


        canvas.setOnMouseClicked(event -> {
            for(int i = 0; i < collection.size(); i++){
                if(Objects.equals(idColumn.getCellData(i), vehicle.getId())){
                    vehiclesTable.getSelectionModel().select(i, idColumn);
                }
            }
        });

        return canvas;
    }


    public void selectedFilterNone(){
        selectedFilterColumn = null;
        filter();
    }
    public void selectedFilterID(){
        selectedFilterColumn = "ID";
        filter();
    }
    public void selectedFilterName(){
        selectedFilterColumn = "NAME";
        filter();
    }
    public void selectedFilterKey(){
        selectedFilterColumn = "KEY";
        filter();
    }

    public void filter(){
        String s = findField.getText();
        if(selectedFilterColumn == null || s.equals("")) updateTableContent(collection);
        else{
            ObservableList<AppVehicle> filteredCollection = FXCollections.observableArrayList();

            switch (selectedFilterColumn){
                case ("ID"):
                    try{
                        Long id = Long.parseLong(s);
                        collection.stream().filter(veh -> veh.getId().equals(id)).forEach(filteredCollection::add);
                    }
                    catch (Exception e){}
                    break;

                case ("NAME"):
                    try{
                        collection.stream().filter(veh -> veh.getName().contains(s)).forEach(filteredCollection::add);
                    }
                    catch (Exception e){}
                    break;

                case ("KEY"):
                    try{
                        collection.stream().filter(veh -> veh.getKey().startsWith(s)).forEach(filteredCollection::add);
                    }
                    catch (Exception e){}
                    break;
            }

            updateTableContent(filteredCollection);
        }
    }

    public void updateServerConnection(){
        boolean isConnected = ClientConnectionHandler.isConnected();
        menuServerLogIn.setDisable(!isConnected);
        menuServerRegister.setDisable(!isConnected);
        menuCollectionInfo.setDisable(!isConnected);
        menuCollectionAdd.setDisable(!isConnected);
        menuCollectionSumOfWheels.setDisable(!isConnected);

        if(isConnected)
            if (user != null) {
                connectionStatusLabel.setText(LocalResources.rb.getString("connectionStatusLabel") + ": " + LocalResources.rb.getString("authorizedAs") + " " + AppController.user);
            }
            else
                connectionStatusLabel.setText(LocalResources.rb.getString("connectionStatusLabel") + ": " + LocalResources.rb.getString("connected"));
        else
            connectionStatusLabel.setText(LocalResources.rb.getString("connectionStatusLabel")+": "+LocalResources.rb.getString("notConnected"));
    }

    public void connectionLostError(){
        if(!ClientConnectionHandler.isConnected()) {
            if(updateResult != null) updateResult.cancel(true);

            collection.clear();
            user = null;
            password = null;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(LocalResources.rb.getString("error"));
            alert.setContentText(LocalResources.rb.getString("connectionLost") + "!");
            alert.showAndWait();
            updateServerConnection();
            updateTableContent(collection);
        }
    }

    public void updateCollectionFromServer(){
        if(ClientConnectionHandler.isConnected() && (selectedFilterColumn == null ||  findField.getText().equals(""))) {
            try {
                ClientConnectionHandler.write(new Request(user, password, new Show()));
                Reply reply = ClientConnectionHandler.read();

                if (!reply.isSuccessful()) throw new RuntimeException("Reply is error");


                ArrayList<Vehicle> data = (ArrayList<Vehicle>) reply.getData();
                if(!data.equals(prevData)){
                    collection.clear();
                    data.stream().map(veh -> new AppVehicle(veh)).forEach(veh -> collection.add(veh));

                    updateTableContent(collection);
                    updateMap(collection);
                }
                prevData.clear();
                prevData.addAll(data);

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
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegistrationWindow.fxml"));
            Parent root = loader.load();
            RegistrationController controller= loader.getController();
            controller.updateLabels();
            stage.setScene(new Scene(root));
            stage.setTitle(LocalResources.rb.getString("registerWindow"));
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

            if(updateResult != null) updateResult.cancel(true);

            updateResult = scheduler.scheduleWithFixedDelay(() -> Platform.runLater(() -> {
                if (ClientConnectionHandler.isConnected() && !scheduler.isTerminated()) {
                    updateCollectionFromServer();
                }
            }), 0, 5, TimeUnit.SECONDS);
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
