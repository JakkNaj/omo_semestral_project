package cz.fel.cvut.omo.house;

public class Door {

    private final Room room1;
    private final Room room2;

    private boolean closed = true;

    public Door(Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
    }

    public Room getSecondRoom(Room room) {
        return room == room1 ? room2 : room1;
    }

    public void open(){
        closed = false;
    }

    public void close(){
        closed = true;
    }
}
