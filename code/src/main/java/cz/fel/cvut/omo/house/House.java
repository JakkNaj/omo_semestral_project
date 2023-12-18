package cz.fel.cvut.omo.house;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.fel.cvut.omo.events.Event;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Class representing the whole House used in simulation.
 */
public class House {

    //Singleton
    private static House INSTANCE;

    private List<Event> events;

    private List<Floor> floors;

    private House(){}

    public House getInstance() {
        if (INSTANCE == null)
            INSTANCE = new House();
        return INSTANCE;
    }

    public static House loadFromJson(String file) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(new File(file), House.class);
    }
}
