package lab8.Client.VehicleCollectionClient.Resources;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalResources {

    public static ResourceBundle rb = ResourceBundle.getBundle("lab8.Client.VehicleCollectionClient.Resources.Labels");

    public static void defaulResources(){
        ResourceBundle.clearCache();
        rb = ResourceBundle.getBundle("lab8.Client.VehicleCollectionClient.Resources.Labels");
    }

    public static void changeResources(String language, String country){
        ResourceBundle.clearCache();
        if(country == null)
            rb = ResourceBundle.getBundle("lab8.Client.VehicleCollectionClient.Resources.Labels", new Locale(language));
        else
            rb = ResourceBundle.getBundle("lab8.Client.VehicleCollectionClient.Resources.Labels", new Locale(language, country));
    }

    public static void changeResources(String language){ changeResources(language, null);}
}
