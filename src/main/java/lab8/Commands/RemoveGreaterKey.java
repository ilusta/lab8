package lab8.Commands;

import lab8.Essentials.Reply;
import lab8.Exceptions.CommandExecutionException;
import lab8.Exceptions.EOFInputException;
import lab8.Exceptions.InputException;
import lab8.Server.VehicleCollectionServer.VehicleCollection;

public class RemoveGreaterKey extends SecurityCollectionCommand
{
    @Override
    public String getName() {
        return "remove_greater_key";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "[key] | Removes element from collection with key greater than given.";
    }

    public static void attach(VehicleCollection collection){
        RemoveGreaterKey.collection = collection;
    }

    private String removeKey;

    @Override
    public Command build(String[] params) throws InputException, EOFInputException {
        if (params.length < 2) throw new InputException("Key is missing");
        removeKey = params[1];

        return this;
    }

    @Override
    public Reply execute() throws CommandExecutionException {
        return collection.removeGreaterKey(removeKey, this.getUser());
    }
}