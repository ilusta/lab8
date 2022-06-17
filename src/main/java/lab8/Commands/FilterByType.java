package lab8.Commands;

import lab8.Essentials.Reply;
import lab8.Exceptions.CommandExecutionException;
import lab8.Exceptions.EOFInputException;
import lab8.Exceptions.InputException;
import lab8.Server.VehicleCollectionServer.VehicleCollection;

public class FilterByType extends CollectionCommand
{
    @Override
    public String getName() {
        return "filter_by_type";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "[type] | Prints collection filtered by given vehicle type.";
    }

    public static void attach(VehicleCollection collection){
        FilterByType.collection = collection;
    }

    private String type = null;

    @Override
    public Command build(String[] params) throws InputException, EOFInputException {
        if (params.length < 2) throw new InputException("Vehicle type is missing");
        type = params[1];
        return this;
    }

    @Override
    public Reply execute() throws CommandExecutionException {
        return collection.filterByType(type);
    }
}