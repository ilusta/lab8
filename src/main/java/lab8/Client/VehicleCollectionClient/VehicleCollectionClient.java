package lab8.Client.VehicleCollectionClient;

import java.awt.event.ActionEvent;
import java.io.*;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lab8.Client.VehicleCollectionClient.Resources.LocalResources;
import lab8.Commands.*;
import lab8.Essentials.Request;
import lab8.Exceptions.CommandExecutionException;
import lab8.Exceptions.ConnectionException;
import lab8.Exceptions.EOFInputException;
import lab8.Commands.CommandBuilder;
import lab8.Commands.CommandExecutor;
import lab8.UserInput.UserInput;
import java.util.ArrayList;
import java.util.Locale;

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
        primaryStage.setTitle("Vehicle collection app");
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit(primaryStage);
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




    private static final ArrayList<Command> commandList = new ArrayList<>();
    private static final ArrayList<Command> allCommandList = new ArrayList<>();

    private String user = null;
    private String password = null;


    public void run(String[] args) {

        launch(args);
        /*
        System.out.println("Welcome to the Vehicle Collection Client!");
        UserInput.setDefaultReader(new BufferedReader(new InputStreamReader(System.in)));

        commandList.add(new Help());
        commandList.add(new Connect());
        commandList.add(new Disconnect());
        commandList.add(new Exit());
        commandList.add(new History());
        commandList.add(new ExecuteScript());

        Help.attachCommandList(commandList);

        CommandBuilder commandBuilder = new CommandBuilder();
        CommandExecutor executor = new CommandExecutor();
        CommandBuilder.setCommandList(commandList);

        System.out.println("Initialization complete");
        while (Exit.getRunFlag()) {
            try {
                if(UserInput.getFilesStackSize() == 0) System.out.print("->");
                Command command = commandBuilder.build();

                if (!commandList.contains(command)) {
                    if(command instanceof LogIn || command instanceof RegisterUser){
                        SecurityCommand c = (SecurityCommand) command;
                        user = c.getUser();
                        password = c.getPassword();
                    }

                    if (ClientConnectionHandler.isConnected()) {
                        ClientConnectionHandler.write(new Request(user, password, command));
                        System.out.println(ClientConnectionHandler.read());
                    }
                    else
                        throw new CommandExecutionException("Not connected to the server");
                }
                else {
                    System.out.println(executor.execute(command));
                }
            } catch (Exception e) {
                if (e instanceof EOFInputException) {
                    if(UserInput.getFilesStackSize() > 0){
                        UserInput.removeReader();
                        continue;
                    }
                    break;
                }
                System.out.println("Error: " + e);
                if(e instanceof ConnectionException) disconnect();
            }
        }

*/
        ClientConnectionHandler.disconnect();
        UserInput.removeReader();
        System.out.println("Goodbye!");
    }


    public static void disconnect(){
        ClientConnectionHandler.disconnect();

        allCommandList.clear();
        allCommandList.addAll(commandList);
        Help.attachCommandList(allCommandList);
        CommandBuilder.setCommandList(allCommandList);
        System.out.println("\tServer commands deleted from list");
    }

    public static void connect(String[] args){
        try {
            ClientConnectionHandler.connect(args);

            System.out.println("Receiving available commands:");
            allCommandList.clear();
            allCommandList.addAll(commandList);

            int counter = 0;
            while (true) {
                Object obj = ClientConnectionHandler.read();
                if (obj instanceof String && obj.equals("End"))
                    break;
                else if (obj instanceof Command) {
                    counter++;
                    allCommandList.add((Command) obj);
                }
            }
            Help.attachCommandList(allCommandList);
            CommandBuilder.setCommandList(allCommandList);
            System.out.println("\t" + counter + " commands received");
        }
        catch (Exception e){
            System.out.println("\tError occurred while connecting to server: " + e);
            disconnect();
            throw new RuntimeException();
        }
    }
}