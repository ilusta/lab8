package lab8.Client.VehicleCollectionClient;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lab8.Client.VehicleCollectionClient.Resources.LocalResources;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class VehicleCollectionClient extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception{
        LocalResources.defaulResources();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        Parent root = loader.load();
        AppController controller = loader.getController();
        controller.updateLabels();
        controller.initTable();
        primaryStage.setTitle("Vehicle collection app");
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit(primaryStage);
            controller.scheduler.shutdown();
        });
    }

    public void exit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LocalResources.rb.getString("attention")+"!");
        alert.setContentText(LocalResources.rb.getString("closeApp")+"?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

    public void run(String[] args) {
        launch(args);

        ClientConnectionHandler.disconnect();
        System.out.println("Goodbye!");
    }
}