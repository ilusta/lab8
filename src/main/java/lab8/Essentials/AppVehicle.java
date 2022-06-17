package lab8.Essentials;

import javafx.beans.property.*;
import lab8.Essentials.Vehicle.Vehicle;
import lab8.Essentials.Vehicle.VehicleType;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppVehicle {
    private SimpleLongProperty id;
    private SimpleStringProperty key;
    private SimpleStringProperty name;
    private SimpleStringProperty user;
    private SimpleIntegerProperty x;
    private SimpleObjectProperty<Integer> y;
    private SimpleStringProperty date;
    private SimpleObjectProperty<Double> enginePower;
    private SimpleObjectProperty<Long> numberOfWheels;
    private SimpleDoubleProperty capacity;
    private SimpleObjectProperty<VehicleType> type;

    public AppVehicle(Long id,
                      String key,
                      String name,
                      String user,
                      Integer x,
                      Integer y,
                      ZonedDateTime creationDate,
                      Double enginePower,
                      Long numberOfWheels,
                      Double capacity,
                      VehicleType type){
        this.id = new SimpleLongProperty(id);
        this.key = new SimpleStringProperty(key);
        this.name = new SimpleStringProperty(name);
        this.user = new SimpleStringProperty(user);
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleObjectProperty<>(y);
        this.date = new SimpleStringProperty(creationDate.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        this.enginePower = new SimpleObjectProperty<>(enginePower);
        this.numberOfWheels = new SimpleObjectProperty<>(numberOfWheels);
        this.capacity = new SimpleDoubleProperty(capacity);
        this.type = new SimpleObjectProperty<>(type);
    }

    public AppVehicle(Vehicle vehicle){
        this.id = new SimpleLongProperty(vehicle.getID());
        this.key = new SimpleStringProperty(vehicle.getKey());
        this.name = new SimpleStringProperty(vehicle.getName());
        this.user = new SimpleStringProperty(vehicle.getUser());
        this.x = new SimpleIntegerProperty(vehicle.getCoordinates().getXCoordinate());
        this.y = new SimpleObjectProperty<>(vehicle.getCoordinates().getYCoordinate());
        this.date = new SimpleStringProperty(vehicle.getCreationDate().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        this.enginePower = new SimpleObjectProperty<>(vehicle.getEnginePower());
        this.numberOfWheels = new SimpleObjectProperty<>(vehicle.getNumberOfWheels());
        this.capacity = new SimpleDoubleProperty(vehicle.getCapacity());
        this.type = new SimpleObjectProperty<>(vehicle.getType());
    }

    public Long getId(){return id.get();}
    public String getKey(){return key.get();}
    public String getName(){return name.get();}
    public String getUser(){return user.get();}
    public Integer getX(){return x.get();}
    public Integer getY(){return y.get();}
    public String getDate(){return date.get();}
    public Double getEnginePower(){return enginePower.get();}
    public Long getNumberOfWheels(){return numberOfWheels.get();}
    public Double getCapacity(){return capacity.get();}
    public VehicleType getType(){return type.get();}

    public void setId(Long id){this.id.set(id);}
    public void setKey(String key){this.key.set(key);}
    public void setName(String name){this.name.set(name);}
    public void setUser(String user){this.user.set(user);}
    public void setX(Integer x){this.x.set(x);}
    public void setY(Integer y){this.y.set(y);}
    public void setDate(ZonedDateTime date){this.date.set(date.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));}
    public void setEnginePower(Double enginePower){this.enginePower.set(enginePower);}
    public void setNumberOfWheels(Long number){this.numberOfWheels.set(number);}
    public void setCapacity(Double capacity){this.capacity.set(capacity);}
    public void setType(VehicleType type){this.type.set(type);}
}
