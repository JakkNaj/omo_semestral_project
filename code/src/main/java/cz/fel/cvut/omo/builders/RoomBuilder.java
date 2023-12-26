package cz.fel.cvut.omo.builders;

import cz.fel.cvut.omo.house.Room;
import cz.fel.cvut.omo.house.Window;

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
}
