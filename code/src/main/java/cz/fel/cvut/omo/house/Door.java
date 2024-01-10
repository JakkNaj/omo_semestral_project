package cz.fel.cvut.omo.house;

import com.google.gson.annotations.Expose;
import lombok.Getter;

import static java.time.LocalTime.now;

@Getter
public class Door {

    @Expose(serialize = false, deserialize = false)
    private final Room room1;
    @Expose(serialize = false, deserialize = false)
    private final Room room2;

    @Expose(serialize = false, deserialize = false)
    @Getter
    private final int id;

    @Getter
    private boolean closed = true;

    public Door(Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
        id = now().getNano();
    }

    public Door(Room room1, Room room2, int id, boolean closed) {
        this.room1 = room1;
        this.room2 = room2;
        this.id = id;
        this.closed = closed;
    }

    public Room getSecondRoom(Room room) {
        return room == room1 ? room2 : room1;
    }

    public void open() {
        closed = false;
    }

    public void close() {
        closed = true;
    }
}
