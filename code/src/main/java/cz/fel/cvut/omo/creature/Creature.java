package cz.fel.cvut.omo.creature;

import cz.fel.cvut.omo.house.Door;
import cz.fel.cvut.omo.house.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

/**
 * Abstract class for all living beings inside the simulation
 */
@Getter
@Setter
public abstract class Creature implements CreatureInterface {

    private boolean alive = true;

    private int age = 0;

    private boolean sleeping = false;

    private Random rand = new Random();

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

    public void move(Room room){
        //chance of moving to other room
        if(rand.nextInt(3) == 0){
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
}
