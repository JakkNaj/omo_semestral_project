package cz.fel.cvut.omo.house;

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
}
