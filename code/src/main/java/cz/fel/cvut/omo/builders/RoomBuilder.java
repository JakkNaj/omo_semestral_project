package cz.fel.cvut.omo.builders;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.house.Room;
import cz.fel.cvut.omo.house.Window;
import cz.fel.cvut.omo.vehicles.Vehicle;

public class RoomBuilder {
    private final Room room;

    public Room build(){
        return room;
    }

    public RoomBuilder(String name){
        room = new Room(name);
    }

    public RoomBuilder addWindow(Window window){
        room.addWindow(window);
        return this;
    }

    public RoomBuilder addAppliance(Appliance appliance){
        room.addAppliance(appliance);
        return this;
    }

    public RoomBuilder addVehicle(Vehicle vehicle){
        room.addVehicle(vehicle);
        return this;
    }
}
