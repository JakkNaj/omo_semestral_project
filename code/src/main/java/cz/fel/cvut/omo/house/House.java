package cz.fel.cvut.omo.house;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.report.ReportVisitor;
import cz.fel.cvut.omo.vehicles.Vehicle;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Class representing the whole House used in simulation.
 */
public class House {

    //Singleton
    private static House INSTANCE;

    @Getter
    private final List<Floor> floors = new ArrayList<>();

    private House() {
    }

    public static House getInstance() {
        if (INSTANCE == null)
            INSTANCE = new House();
        return INSTANCE;
    }

    public static House loadFromJson(String file) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(new File(file), House.class);
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public void accept(ReportVisitor reportVisitor) {
        reportVisitor.visit(this);
        for (int i = 0; i < floors.size(); i++) {
            floors.get(i).accept(reportVisitor, i);
        }
        ;
    }

    public Room getCreatureRoom(Creature creature) {
        AtomicReference<Room> result = new AtomicReference<>();
        floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                if (room.getCreatures().contains(creature)) {
                    result.set(room);
                }
            });
        });
        return result.get();
    }

    public Room getApplianceRoom(Appliance appliance) {
        AtomicReference<Room> result = new AtomicReference<>();
        floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                if (room.getAppliances().contains(appliance)) {
                    result.set(room);
                }
            });
        });
        return result.get();
    }

    public Room getVehicleRoom(Vehicle vehicle) {
        AtomicReference<Room> result = new AtomicReference<>();
        floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                if (room.getVehicles().contains(vehicle)) {
                    result.set(room);
                }
            });
        });
        return result.get();
    }

    public List<Creature> getAllCreatures() {
        List<Creature> creatures = new ArrayList<>();
        floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                creatures.addAll(room.getCreatures());
            });
        });
        return creatures;
    }

    public List<Appliance> getAllAppliances() {
        List<Appliance> appliances = new ArrayList<>();
        floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                appliances.addAll(room.getAppliances());
            });
        });
        return appliances;
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                vehicles.addAll(room.getVehicles());
            });
        });
        return vehicles;
    }

    public List<Room> getAllRooms(){
        List<Room> rooms = new ArrayList<>();
        floors.forEach(floor -> {
            rooms.addAll(floor.getRooms());
        });
        return rooms;
    }
}
