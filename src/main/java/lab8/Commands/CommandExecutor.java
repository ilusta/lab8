package lab8.Commands;

import lab8.Exceptions.InputException;


public class CommandExecutor {

    public String execute(Command command){
        String message = "";
        try{
            if (command == null)
                throw new InputException("Command is NULL.");

            message += command.execute();
            History.add(command.getName());
        }
        catch (Exception e) {
           message += "Error occurred while executing command: " + e.getMessage() + "\n";
        }

        return message;
    }
}
