package lab8.Client.VehicleCollectionClient.Resources;

import java.util.ListResourceBundle;

public class Labels extends ListResourceBundle {
    public Object[][] getContents() {return contents;}
    private Object[][] contents = {
            {"menuFile", "File"},
            {"menuCollection", "Collection"},
            {"menuHelp", "Help"},
            {"menuFileChangeLang", "Change language"},
            {"menuFileExit", "Exit"},
            {"menuServer", "Server"},
            {"menuServerConnect", "Connect"},
            {"menuServerLogIn", "Log in"},
            {"menuServerRegister", "Register"},
            {"menuCollectionInfo", "Information"},
            {"menuCollectionAdd", "Add vehicle"},
            {"menuCollectionSort", "Sort"},
            {"menuCollectionSortByType", "By type"},
            {"menuCollectionSortByName", "By name"},
            {"menuHelpAbout", "About"},

            {"connectionStatusLabel", "Connection status"},
            {"collectionInfoLabel", "Collection information"},

            {"emptyTable", "No vehicles in collection"},
            {"idColumn", "ID"},
            {"keyColumn", "Key"},
            {"userColumn", "User"},
            {"nameColumn", "Name"},
            {"coordinatesColumn", "Coordinates"},
            {"coordinatesXColumn", "X"},
            {"coordinatesYColumn", "Y"},
            {"dateColumn", "Date"},
            {"enginePowerColumn", "Power"},
            {"numberOfWheelsColumn", "Number of wheels"},
            {"capacityColumn", "Capacity"},
            {"typeColumn", "Type"},
            {"addVehicleButton", "Add vehicle"},
            {"attention", "Attention"},
            {"closeApp", "Close Vehicle collection application"},

            {"connectToServerWindow", "Connect to server"},
            {"enterIpAndPort", "Enter server`s IP and port"},
            {"cancelButton", "Cancel"},
            {"connectButton", "Connect"},
            {"unableToConnect", "Unable to connect, try again"},
            {"connected", "connected to server"},
            {"notConnected", "not connected"}};
}
