package lab8.Commands;

import lab8.Essentials.AppVehicle;
import lab8.Essentials.Reply;
import lab8.Exceptions.CommandExecutionException;
import lab8.Exceptions.EOFInputException;
import lab8.Exceptions.InputException;
import lab8.Server.VehicleCollectionServer.VehicleCollection;
import lab8.Essentials.Vehicle.Vehicle;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

    public void addVehicle(AppVehicle v){
        try {
            vehicle = new Vehicle(
                    v.getKey(),
                    v.getId(),
                    v.getName(),
                    v.getX(),
                    v.getY(),
                    ZonedDateTime.parse(v.getDate(), DateTimeFormatter.ISO_ZONED_DATE_TIME),
                    v.getEnginePower(),
                    v.getNumberOfWheels(),
                    v.getCapacity(),
                    v.getType(),
                    v.getUser()
            );
        } catch (Exception e) {}
    }

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
    public Reply execute() throws CommandExecutionException{
        return collection.update(vehicle, this.getUser());
    }
}