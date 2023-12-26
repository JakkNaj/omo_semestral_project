package cz.fel.cvut.omo.builders;

import cz.fel.cvut.omo.house.Door;
import cz.fel.cvut.omo.house.Floor;
import cz.fel.cvut.omo.house.Room;

public class FloorBuilder {
    private final Floor floor;

    public FloorBuilder(int level){
        floor = new Floor(level);
    }

    public Floor build(){
        return floor;
    }

    public FloorBuilder addRoom(Room room){
        floor.addRoom(room);
        return this;
    }

    public FloorBuilder addDoor(String name1, String name2){
        Door door = new Door(floor.getRoom(name1), floor.getRoom(name2));
        floor.getRoom(name1).addDoor(door);
        floor.getRoom(name2).addDoor(door);
        return this;
    }
}
