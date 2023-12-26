package cz.fel.cvut.omo.house;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.fel.cvut.omo.events.Event;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class representing the whole House used in simulation.
 */
public class House {

    //Singleton
    private static House INSTANCE;

    private final List<Event> events = new ArrayList<>();

    @Getter
    private final List<Floor> floors = new ArrayList<>();

    private House(){}

    public static House getInstance() {
        if (INSTANCE == null)
            INSTANCE = new House();
        return INSTANCE;
    }

    public static House loadFromJson(String file) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(new File(file), House.class);
    }

    public void addFloor(Floor floor){
        floors.add(floor);
    }
}
