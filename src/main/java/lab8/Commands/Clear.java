package lab8.Commands;

import lab8.Essentials.Reply;
import lab8.Exceptions.CommandExecutionException;
import lab8.Server.VehicleCollectionServer.VehicleCollection;


public class Clear extends SecurityCollectionCommand
{

    @Override
    public String getName() {
        return "clear";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "Clears collection.";
    }

    public static void attach(VehicleCollection collection){
        Clear.collection = collection;
    }

    @Override
    public Command build(String[] params){
        return this;
    }

    @Override
    public Reply execute() throws CommandExecutionException {
        return collection.clear(this.getUser());
    }

}