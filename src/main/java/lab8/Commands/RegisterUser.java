package lab8.Commands;

import lab8.Essentials.Reply;
import lab8.Exceptions.CommandExecutionException;
import lab8.Exceptions.InputException;
import lab8.Server.VehicleCollectionServer.VehicleCollectionServer;


public class RegisterUser extends SecurityCommand
{

    @Override
    public String getName() {
        return "register";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "[username password] | register at this server";
    }


    @Override
    public Command build(String[] params) throws InputException{
        if(params.length < 3) throw new InputException("Not enough arguments. See 'help' and try again.");

        user = params[1];
        password = params[2];
        if(user == null || password == null) throw new InputException("Username and password can not be null.");

        return this;
    }

    @Override
    public Reply execute() throws CommandExecutionException {
        return VehicleCollectionServer.registerUser(user, password);
    }

}