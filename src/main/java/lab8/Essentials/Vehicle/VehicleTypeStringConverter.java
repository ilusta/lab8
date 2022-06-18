package lab8.Essentials.Vehicle;

import javafx.util.StringConverter;

public class VehicleTypeStringConverter extends StringConverter<VehicleType> {
    @Override public VehicleType fromString(String value){
        // If the specified value is null or zero-length, return null
        if (value == null) {
            return null;
        }

        value = value.trim();

        if (value.length() < 1) {
            return null;
        }

        VehicleType type = null;

        try{
            type = VehicleType.valueOf(value);
        } catch(Exception e){}

        return type;
    }

    @Override public String toString(VehicleType value) {
        // If the specified value is null, return a zero-length String
        if (value == null) {
            return "";
        }

        return value.toString();
    }
}
