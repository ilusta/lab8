package lab8.Server.VehicleCollectionServer;

import lab8.Essentials.AppVehicle;
import lab8.Essentials.Reply;
import lab8.Exceptions.CommandExecutionException;

import java.sql.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import lab8.Essentials.Vehicle.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class VehicleCollection {

    private static final Logger logger = LogManager.getLogger(VehicleCollection.class);

    private Connection connection;

    private ArrayList<Vehicle> collection;
    private ZonedDateTime creationDate;

    ReadWriteLock rwLock = new ReentrantReadWriteLock();


    public VehicleCollection(Connection connection) throws RuntimeException{
        this.connection = connection;
        this.collection = new ArrayList<>();
        Vehicle.setCollection(collection);
        this.creationDate = ZonedDateTime.now();

        load();
    }

    public void load(){
        logger.info("Loading collection from database:");
        rwLock.readLock().lock();
        collection.clear();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, key, name, x, y, date, enginepower, numberofwheels, capacity, type, \"user\" from collection")) {

            while (resultSet.next()) {
                try {
                    Long id = resultSet.getLong("id");
                    String key = resultSet.getString("key");
                    String name = resultSet.getString("name");
                    Integer x = resultSet.getInt("x");
                    Integer y = resultSet.getInt("y");
                    if(resultSet.wasNull()) y = null;
                    ZonedDateTime date = ZonedDateTime.parse(resultSet.getString("date"), DateTimeFormatter.ISO_ZONED_DATE_TIME);
                    Double enginePower = resultSet.getDouble("enginepower");
                    if(resultSet.wasNull()) enginePower = null;
                    Long numberOfWheels = resultSet.getLong("numberofwheels");
                    if(resultSet.wasNull()) numberOfWheels = null;
                    Double capacity = resultSet.getDouble("capacity");
                    VehicleType type = VehicleType.valueOf(resultSet.getString("type"));
                    String user = resultSet.getString("user");

                    collection.add(new Vehicle(key, id, name, x, y, date, enginePower, numberOfWheels, capacity, type, user));

                    if(this.creationDate.compareTo(date) > 0) this.creationDate = date;
                }
                catch(Exception e){
                    logger.error("\tError while loading vehicle from collection: " + e);
                }
            }
            logger.info("\tLoaded " + collection.size() + " vehicles");
        }
        catch (Exception e){
            throw new RuntimeException("Unable to load collection from database: " + e);
        }
        finally {
            rwLock.readLock().unlock();
        }
    }


    public Reply show() {
        return new Reply(true, collection);
    }


    public Reply insert(Vehicle vehicle, String user) {
        rwLock.readLock().lock();
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                "collection(id, key, name, x, y, date, enginepower, numberofwheels, capacity, type, \"user\") VALUES (nextval('collection_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")){

            preparedStatement.setString(1, vehicle.getKey());
            preparedStatement.setString(2, vehicle.getName());
            preparedStatement.setInt(3, vehicle.getCoordinates().getXCoordinate());
            Integer y = vehicle.getCoordinates().getYCoordinate();
            if(y != null) preparedStatement.setInt(4, y);
            else preparedStatement.setNull(4, Types.INTEGER);
            preparedStatement.setString(5, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
            Double ep = vehicle.getEnginePower();
            if(ep != null) preparedStatement.setDouble(6, ep);
            else preparedStatement.setNull(6, Types.DOUBLE);
            Long nofw = vehicle.getNumberOfWheels();
            if(nofw != null) preparedStatement.setLong(7, nofw);
            else preparedStatement.setNull(7, Types.BIGINT);
            preparedStatement.setDouble(8, vehicle.getCapacity());
            preparedStatement.setString(9, vehicle.getType().name());
            preparedStatement.setString(10, user);

            preparedStatement.execute();

            return new Reply(true);
        }
        catch (Exception e){
            return new Reply(false, "Unable to add vehicle to collection: " + e);
        }
        finally {
            rwLock.readLock().unlock();
            load();
        }
    }


    public Reply update(Vehicle vehicle, String user) {
        rwLock.readLock().lock();
        Long givenID = vehicle.getID();
        Optional<Vehicle> foundVehicle = collection.stream().filter(veh -> veh.getID().equals(givenID)).findFirst();

        if(!foundVehicle.isPresent())
            return new Reply(false, "No vehicle with ID=" + givenID +" in collection");
        if(!foundVehicle.get().getUser().equals(user))
            return new Reply(false, "Vehicle with ID=" + givenID +" belongs to another user");

        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE collection" +
                "SET name=?, x=?, y=?, date=?, enginepower=?, numberofwheels=?, capacity=?, type=? " +
                "WHERE (id=? AND \"user\" =?);")){

            preparedStatement.setString(1, vehicle.getName());
            preparedStatement.setInt(2, vehicle.getCoordinates().getXCoordinate());
            Integer y = vehicle.getCoordinates().getYCoordinate();
            if(y != null) preparedStatement.setInt(3, y);
            else preparedStatement.setNull(3, Types.INTEGER);
            preparedStatement.setString(4, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
            Double ep = vehicle.getEnginePower();
            if(ep != null) preparedStatement.setDouble(5, ep);
            else preparedStatement.setNull(5, Types.DOUBLE);
            Long nofw = vehicle.getNumberOfWheels();
            if(nofw != null) preparedStatement.setLong(6, nofw);
            else preparedStatement.setNull(6, Types.BIGINT);
            preparedStatement.setDouble(7, vehicle.getCapacity());
            preparedStatement.setString(8, vehicle.getType().name());
            preparedStatement.setLong(9, givenID);
            preparedStatement.setString(10, user);

            preparedStatement.execute();

            return new Reply(true);
        }
        catch (Exception e){
            return new Reply(false, "Unable to update vehicle: " + e);
        }
        finally {
            rwLock.readLock().unlock();
            load();
        }
    }


    public Reply removeKey(String givenKey, String user) {
        rwLock.readLock().lock();
        Optional<Vehicle> foundVehicle = collection.stream().filter(veh -> veh.getKey().equals(givenKey)).findFirst();

        if(!foundVehicle.isPresent())
            return new Reply(false, "No vehicle with KEY=" + givenKey +" in collection");
        if(!foundVehicle.get().getUser().equals(user))
            return new Reply(false, "Vehicle with KEY=" + givenKey +" belongs to another user");


        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM collection WHERE (key=? AND \"user\"=?);")){

            preparedStatement.setString(1, givenKey);
            preparedStatement.setString(2, user);
            preparedStatement.execute();

            return new Reply(true);
        }
        catch (Exception e){
            return new Reply(false, "Unable to remove vehicle: " + e);
        }
        finally {
            rwLock.readLock().unlock();
            load();
        }
    }


    public Reply clear(String user) {
        rwLock.readLock().lock();
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM collection WHERE \"user\"=?;")){
            preparedStatement.setString(1, user);
            preparedStatement.execute();
                        return new Reply(true);
        }
        catch (Exception e){
            return new Reply(false, "Unable to remove vehicles: " + e);
        }
        finally {
            rwLock.readLock().unlock();
            load();
        }
    }


    public Integer getSize() {
        return this.collection.size();
    }


    public Reply getSumOfWheels(){
        rwLock.writeLock().lock();
        try {
            Long sumOfWheels = collection.stream().reduce(0L, (sum, vehicle) -> {
                if (vehicle.getNumberOfWheels() != null) return sum + vehicle.getNumberOfWheels();
                else return sum;
            }, Long::sum);
            return new Reply(true, sumOfWheels);
        }
        catch (Exception e){
            return new Reply(false);
        }
        finally {
            rwLock.writeLock().unlock();
        }
    }


    public Reply removeLower(Vehicle givenVehicle, String user) {
        rwLock.readLock().lock();
        Long[] IDArr = collection.stream().filter(veh -> ((veh.compareTo(givenVehicle) < 0) && veh.getUser().equals(user))).map(Vehicle::getID).toArray(Long[]::new);
        if(IDArr.length == 0) return new Reply(false, "No smaller vehicles owned by " + user + " in collection");

        int counter = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM collection WHERE (\"user\"=? AND id=?)")){

            preparedStatement.setString(1, user);
            for(Long id : IDArr){
                preparedStatement.setLong(2, id);
                preparedStatement.execute();
                counter++;
            }

            return new Reply(true);
        }
        catch (Exception e){
            return new Reply(false, "Unable to remove vehicles: " + e);
        }
        finally {
            rwLock.readLock().unlock();
            load();
        }
    }


    public Reply removeGreaterKey(String givenKey, String user) {
        rwLock.readLock().lock();
        Long[] IDArr = collection.stream().filter(veh -> ((veh.getKey().compareTo(givenKey) > 0) && veh.getUser().equals(user))).map(Vehicle::getID).toArray(Long[]::new);
        if(IDArr.length == 0) return new Reply(false, "No vehicles with greater keys owned by " + user + " in this collection");

        int counter = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM collection WHERE (\"user\"=? AND id=?)")){

            preparedStatement.setString(1, user);
            for(Long id : IDArr){
                preparedStatement.setLong(2, id);
                preparedStatement.execute();
                counter++;
            }
            return new Reply(true);
        }
        catch (Exception e){
            return new Reply(false, "Unable to remove vehicles: " + e);
        }
        finally {
            rwLock.readLock().unlock();
            load();
        }
    }

    public Reply maxByCoordinates() {
        rwLock.writeLock().lock();
        Optional<Vehicle> opVehicle = collection.stream().max(Comparator.comparing(Vehicle::getCoordinates));
        rwLock.writeLock().unlock();
        if(opVehicle.isPresent())
            return new Reply(true, opVehicle.get(), "Vehicle with biggest coordinates is:\n\t");
        else
            return new Reply(false, "There is no vehicle with biggest coordinates in collection");
    }

    public Reply filterByType(String givenType) {
        rwLock.writeLock().lock();
        try{
            VehicleType type = VehicleType.valueOf(givenType);
            Object[] arr = collection.stream().filter(vehicle -> vehicle.getType().equals(type)).map(AppVehicle::new).toArray();
            return new Reply(true, arr);
        } catch (Exception e){
            return new Reply(false, "Wrong vehicle type. Select one of the following types:\n" + VehicleType.convertToString());
        }
        finally {
            rwLock.writeLock().unlock();
        }
    }

    public ZonedDateTime getCreationDate(){
        return this.creationDate;
    }

    public Reply info(){
        return  new Reply(true,
                "Linked hash map.\n" + this.getSize() + " vehicles.\nCreated at: " + this.getCreationDate().format(DateTimeFormatter.ISO_DATE_TIME), "");
    }
}