package cz.fel.cvut.omo.house;

import cz.fel.cvut.omo.report.ReportVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Class representing Floor in simulation.
 */
@Getter
@Setter
@AllArgsConstructor
public class Floor {

    private int level;

    private List<Room> rooms;

    public Floor(int level){
        this.rooms = new ArrayList<>();
        this.level = level;
    }

    public void addRoom(Room room){
        rooms.add(room);
    }

    /**
     * finds room by its name
     * @param name name of room
     * @return room instance
     */
    public Room getRoom(String name){
        for(Room room : rooms){
            if(room.getName().equals(name)){
                return room;
            }
        }
        throw new IllegalArgumentException("no room called " + name);
    }

    public void accept(ReportVisitor reportVisitor, int i){
        reportVisitor.visit(this, i);
        rooms.forEach(room -> {
            room.accept(reportVisitor);
        });
    }
}
