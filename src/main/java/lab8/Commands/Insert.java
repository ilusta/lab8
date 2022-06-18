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

public class Insert extends SecurityCollectionCommand
{

    @Override
    public String getName() {
        return "insert";
    }
    @Override
    public CommandType getType() {
        return CommandType.ALL;
    }
    @Override
    public String getHelp() {
        return "[key] {vehicle} | Inserts new element to collection with given key.";
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
        Insert.collection = collection;
    }

    @Override
    public Command build(String[] params) throws InputException, EOFInputException{
        /*
        if (params.length < 2) throw new InputException("Argument is missing");
        String key = params[1];
        vehicle = new Vehicle(key);

         */
        return this;
    }

    @Override
    public Reply execute() throws CommandExecutionException {
        return collection.insert(vehicle, this.getUser());
    }
}