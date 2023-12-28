package cz.fel.cvut.omo.house;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.report.ReportVisitor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing Room in simulation.
 */
public class Room {

    @Getter
    private final String name;

    private int temperature;

    @Getter
    private final List<Creature> creatures;

    private final List<Door> doors;

    @Getter
    private final List<Window> windows;

    @Getter
    private final List<Appliance> appliances;

    public Room(String name) {
        doors = new ArrayList<>();
        creatures = new ArrayList<>();
        windows = new ArrayList<>();
        appliances = new ArrayList<>();
        this.name = name;
    }

    public void addDoor(Door door){
        doors.add(door);
    }

    public void addWindow(Window window){
        windows.add(window);
    }

    public void addAppliance(Appliance appliance){ appliances.add(appliance); }

    public void accept(ReportVisitor reportVisitor){
        reportVisitor.visit(this);
        appliances.forEach(appliance -> {
            appliance.accept(reportVisitor);
        });

    }
}
