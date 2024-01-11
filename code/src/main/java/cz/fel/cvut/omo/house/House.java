package cz.fel.cvut.omo.house;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.fel.cvut.omo.activities.Activity;
import cz.fel.cvut.omo.activities.Fix;
import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.heater.HeaterImpl;
import cz.fel.cvut.omo.appliances.states.ApplianceState;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.creature.person.Person;
import cz.fel.cvut.omo.json.deserializer.*;
import cz.fel.cvut.omo.json.serializer.*;
import cz.fel.cvut.omo.report.ReportVisitor;
import cz.fel.cvut.omo.vehicles.Vehicle;
import lombok.Getter;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<Appliance>>() {
                }.getType(), new ApplianceListDeserializer())
                .registerTypeAdapter(new TypeToken<List<Creature>>() {
                }.getType(), new CreatureListDeserializer())
                .registerTypeAdapter(new TypeToken<List<Vehicle>>() {
                }.getType(), new VehicleListDeserializer())
                .registerTypeAdapter(new TypeToken<ApplianceState>() {
                }.getType(), new ApplianceStateDeserializer())
                .registerTypeAdapter(new TypeToken<List<Activity>>() {
                }.getType(), new ActivityDeserializer())
                .create();


        try (Reader reader = new FileReader(file)) {
            House house = gson.fromJson(reader, House.class);

            if (INSTANCE == null) {
                INSTANCE = house;
                addObserversToRoomsAfterDeserializing();
                configureRunnableActionsOfAppliances();
                initRandom();
            }

            for (Object jsonObject1 : new JSONObject(
                    Files.lines(Paths.get(file))
                            .filter(line -> !line.trim().isEmpty())
                            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                            .toString()).getJSONArray("floors")) {
                Floor floor = House.getInstance().getFloorByLevel((Integer) ((JSONObject) jsonObject1).get("level"));
                floor.getRooms().forEach(Room::removeDoors);
                for (Object jsonObject2 : ((JSONObject) jsonObject1).getJSONArray("rooms")) {
                    for (Object object : ((JSONObject) jsonObject2).getJSONArray("doors")) {
                        JSONObject jsonObject3 = (JSONObject) object;
                        int doorId = (Integer) jsonObject3.get("doorId");
                        Room room1 = floor.getRoom(jsonObject3.get("firstRoom").toString());
                        Room room2 = floor.getRoom(jsonObject3.get("secondRoom").toString());
                        Door door = new Door(room1, room2, doorId, (boolean) jsonObject3.get("closed"));
                        if (!room1.hasDoorWithId(doorId)) {
                            room1.addDoor(door);
                        }
                        if (!room2.hasDoorWithId(doorId)) {
                            room2.addDoor(door);
                        }
                    }
                }
            }
            return INSTANCE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void addObserversToRoomsAfterDeserializing() {
        House.INSTANCE.floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                room.getCreatures().forEach(creature -> {
                    if (creature instanceof Person) {
                        room.subscribe((Person) creature);
                    }
                });
                room.getAppliances().forEach(appliance -> {
                    if (appliance instanceof HeaterImpl) {
                        room.subscribe((HeaterImpl) appliance);
                    }
                });
            });
        });
    }

    private static void configureRunnableActionsOfAppliances() {
        House.INSTANCE.floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                room.getAppliances().forEach(Appliance::configureRunnableActions);
            });
        });
    }

    private static void initRandom() {
        House.INSTANCE.floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                room.getCreatures().forEach(creature -> {
                    creature.initRandom();
                    if (creature instanceof Person){
                        ((Person) creature).getTodoActivities().forEach(activity -> {
                            if (activity instanceof Fix){
                                ((Fix) activity).initRandom();
                            }
                        });
                    }
                });
            });
        });

    }


    public void serializeToJson(String file) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT) // Exclude transient fields by default
                .registerTypeAdapter(Door.class, new DoorSerializer()) // circular reference in Doors
                .registerTypeAdapter(new TypeToken<List<Creature>>() {
                }.getType(), new CreatureListSerializer())
                .registerTypeAdapter(new TypeToken<List<Appliance>>() {
                }.getType(), new ApplianceListSerializer())
                .registerTypeAdapter(new TypeToken<List<Vehicle>>() {
                }.getType(), new VehicleListSerializer())
                .registerTypeAdapter(new TypeToken<ApplianceState>() {
                }.getType(), new ApplianceStateSerializer())
                .registerTypeAdapter(new TypeToken<List<Activity>>() {
                }.getType(), new ActivitySerializer())
                .create();


        String json = gson.toJson(this);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * finds room in which creature is
     *
     * @param creature creature
     * @return room instance, in which the creature is
     */

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

    /**
     * finds room, i which the appliance is
     *
     * @param appliance appliance
     * @return room instance, in which the appliance is
     */
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

    /**
     * finds room, i which the vehicle is
     *
     * @param vehicle vehicle
     * @return room instance, in which the vehicle is
     */
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


    /**
     * finds all creatures from house
     *
     * @return all creatures, that live in house
     */
    public List<Creature> getAllCreatures() {
        List<Creature> creatures = new ArrayList<>();
        floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                creatures.addAll(room.getCreatures());
            });
        });
        return creatures;
    }

    /**
     * finds all appliances from house
     *
     * @return all appliances, that are in house
     */
    public List<Appliance> getAllAppliances() {
        List<Appliance> appliances = new ArrayList<>();
        floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                appliances.addAll(room.getAppliances());
            });
        });
        return appliances;
    }

    /**
     * finds all vehicles from house
     *
     * @return all vehicles, that are in house
     */
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        floors.forEach(floor -> {
            floor.getRooms().forEach(room -> {
                vehicles.addAll(room.getVehicles());
            });
        });
        return vehicles;
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        floors.forEach(floor -> {
            rooms.addAll(floor.getRooms());
        });
        return rooms;
    }

    public Floor getFloorByLevel(int level) {
        for (Floor floor : floors) {
            if (floor.getLevel() == level) {
                return floor;
            }
        }
        throw new IllegalArgumentException("no floor " + level);
    }
}
