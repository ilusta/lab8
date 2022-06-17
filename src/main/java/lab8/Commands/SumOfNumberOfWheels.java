package lab8.Commands;

import lab8.Essentials.Reply;
import lab8.Exceptions.CommandExecutionException;
import lab8.Exceptions.EOFInputException;
import lab8.Exceptions.InputException;
import lab8.Server.VehicleCollectionServer.VehicleCollection;

public class SumOfNumberOfWheels extends CollectionCommand
{

    @Override
    public String getName() {
        return "sum_of_number_of_wheels";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "Prints sum of number of wheels of vehicles in collection.";
    }

    public static void attach(VehicleCollection collection){
        SumOfNumberOfWheels.collection = collection;
    }

    @Override
    public Command build(String[] params) throws InputException, EOFInputException {
        return this;
    }

    @Override
    public Reply execute() throws CommandExecutionException {
        return collection.getSumOfWheels();
    }
}