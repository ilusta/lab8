package lab8.Commands;

import lab8.Essentials.Reply;
import lab8.Server.VehicleCollectionServer.VehicleCollection;


public class Info extends CollectionCommand
{

    @Override
    public String getName() {
        return "info";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "Prints information about collection.";
    }

    public static void attach(VehicleCollection collection){
        Show.collection = collection;
    }

    @Override
    public Command build(String[] param){
        return this;
    }

    @Override
    public Reply execute(){
        return collection.info();
    }

}
