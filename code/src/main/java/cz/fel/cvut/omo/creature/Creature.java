package cz.fel.cvut.omo.creature;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.events.BrokenApplianceEvent;
import cz.fel.cvut.omo.events.BrokenVehicleEvent;
import cz.fel.cvut.omo.events.Event;
import cz.fel.cvut.omo.events.WaterBrokeEvent;
import cz.fel.cvut.omo.house.Door;
import cz.fel.cvut.omo.house.Room;
import cz.fel.cvut.omo.vehicles.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

/**
 * Abstract class for all living beings inside the simulation
 * - all Creatures extends this class
 */
@Getter
@Setter
public abstract class Creature implements CreatureInterface {

    private boolean alive = true;

    private int age = 0;

    private boolean sleeping = false;

    private transient Random rand = new Random();

    @Override
    public void age() {
        age++;
    }

    @Override
    public void die() {
        alive = false;
    }

    public void sleep() {
        sleeping = true;
    }

    public void move(Room room) {
        //chance of moving to other room
        if (rand.nextInt(3) == 0) {
            List<Door> doors = room.getDoors();
            int doorNumber = rand.nextInt(doors.size());
            Door door = doors.get(doorNumber);
            Room other = door.getSecondRoom(room);

            door.open();
            room.removeCreature(this);
            other.addCreature(this);
            door.close();

            //TODO log moving to other room, mby do in open and close in Room
        }
    }

    public abstract String getName();

    /**
     * Method for generating event
     * - used to generate BrokenApplianceEvent when breaking the appliance while using it
     */
    public void generateEvent(Room room, Appliance appliance) {
        appliance.turnBroken();
        room.acceptEvent(new BrokenApplianceEvent(room.getName(), "cause why not, " + this.getName(), appliance));
    }

    /**
     * Method for generating event
     * - used to generate BrokenVehicle when breaking the vehicle while using it
     */
    public void generateEvent(Room room, Vehicle vehicle) {
        vehicle.setBroken(true);
        room.acceptEvent(new BrokenVehicleEvent(room.getName(), "cause why not, " + this.getName(), vehicle));
    }

    public void initRandom() {
        rand = new Random();
    }
}
