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
            {"menuCollectionSumOfWheels", "Sum of wheels"},
            {"menuHelpAbout", "About"},

            {"aboutAppWindow", "About"},
            {"informationAboutApp", "This is client application for operations with vehicle collection based on remote server.\nDeveloped by: ilust"},
            {"connectToServerWindow", "Connect to server"},
            {"logInWindow", "Log in"},
            {"registerWindow", "Register"},
            {"collectionInformationWindow", "Information about collection"},
            {"sumOfWheelsWindow", "Sum of wheels in collection"},

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

            {"enterIpAndPort", "Enter server`s IP and port"},
            {"cancelButton", "Cancel"},
            {"connectButton", "Connect"},
            {"error", "Error"},
            {"unableToConnect", "Unable to connect, try again"},
            {"connected", "connected to server"},
            {"notConnected", "not connected"},
            {"errorWhileReceivingCollection", "Error occurred while receiving collection"},
            {"connectionLost", "Connection lost"},

            {"user", "User"},
            {"password", "Password"},
            {"loginButton", "Log in"},
            {"cancelButton", "Cancel"},
            {"unableToLogIn", "Unable to log in"},
            {"authorizedAs", "authorized as"}};
}
