package lab8.Commands;

import lab8.Essentials.Reply;
import lab8.Exceptions.CommandExecutionException;
import lab8.Exceptions.EOFInputException;
import lab8.Exceptions.InputException;

import java.io.Serializable;

public abstract class Command implements Serializable
{
    public abstract String getName();
    public abstract CommandType getType();
    public abstract String getHelp();

    public abstract Reply execute() throws CommandExecutionException;
    public abstract Command build(String[] param) throws InputException, EOFInputException;
}