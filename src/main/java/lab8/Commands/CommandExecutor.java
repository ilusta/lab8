package lab8.Commands;

import lab8.Essentials.Reply;
import lab8.Exceptions.InputException;


public class CommandExecutor {

    public Reply execute(Command command){
        Reply reply = new Reply(false, "executor error");
        try{
            if (command == null)
                throw new InputException("Command is NULL.");

            reply = command.execute();
            History.add(command.getName());
        }
        catch (Exception e) {
           reply = new Reply(false, e.toString());
        }
        return reply;
    }
}
