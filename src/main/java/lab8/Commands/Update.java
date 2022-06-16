package lab8.Commands;

import lab8.Exceptions.CommandExecutionException;
import lab8.Exceptions.EOFInputException;
import lab8.Exceptions.InputException;
import lab8.Server.VehicleCollectionServer.VehicleCollection;
import lab8.Vehicle.Vehicle;

public class Update extends SecurityCollectionCommand
{

    @Override
    public String getName() {
        return "update";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "[ID] {vehicle} | Updates element in collection by it`s ID.";
    }

    private Vehicle vehicle;

    public static void attach(VehicleCollection collection){
        Update.collection = collection;
    }

    @Override
    public Command build(String[] params) throws InputException, EOFInputException{
        if (params.length < 2) throw new InputException("Argument is missing");
        Long ID;
        try {
            ID = Long.parseLong(params[1]);
        }
        catch(NumberFormatException e)
        {
            throw new InputException("Impossible vehicle ID");
        }

        vehicle = new Vehicle(ID);
        return this;
    }

    @Override
    public String execute() throws CommandExecutionException{
        return collection.update(vehicle, this.getUser()) + "\n";
    }
}