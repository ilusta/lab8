package lab8.Commands;

import lab8.Exceptions.CommandExecutionException;
import lab8.Exceptions.EOFInputException;
import lab8.Exceptions.InputException;
import lab8.Server.VehicleCollectionServer.VehicleCollection;
import lab8.Vehicle.Vehicle;

public class RemoveLower extends SecurityCollectionCommand
{
    @Override
    public String getName() {
        return "remove_lower";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "{vehicle} | Removes element from collection that are lower than given.";
    }

    public static void attach(VehicleCollection collection){
        RemoveLower.collection = collection;
    }

    private Vehicle vehicle;

    @Override
    public Command build(String[] params) throws InputException, EOFInputException {
        vehicle = new Vehicle();
        return this;
    }

    @Override
    public String execute() throws CommandExecutionException {
        return collection.removeLower(vehicle, this.getUser()) + "\n";
    }
}