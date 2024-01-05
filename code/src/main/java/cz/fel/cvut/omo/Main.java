package cz.fel.cvut.omo;

import cz.fel.cvut.omo.appliances.coffeeMachine.CoffeeMachineImpl;
import cz.fel.cvut.omo.appliances.fridge.FridgeImpl;
import cz.fel.cvut.omo.appliances.heater.HeaterImpl;
import cz.fel.cvut.omo.appliances.light.LightingImpl;
import cz.fel.cvut.omo.appliances.playstation.PlaystationImpl;
import cz.fel.cvut.omo.appliances.treadmill.TreadmillImpl;
import cz.fel.cvut.omo.appliances.tv.TelevisionImpl;
import cz.fel.cvut.omo.appliances.washingMachine.WashingMachineImpl;
import cz.fel.cvut.omo.builders.FloorBuilder;
import cz.fel.cvut.omo.builders.HouseBuilder;
import cz.fel.cvut.omo.builders.RoomBuilder;
import cz.fel.cvut.omo.builders.WindowBuilder;
import cz.fel.cvut.omo.creature.animal.Animal;
import cz.fel.cvut.omo.creature.animal.AnimalFactory;
import cz.fel.cvut.omo.creature.person.Person;
import cz.fel.cvut.omo.creature.person.PersonFactory;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.house.Room;
import cz.fel.cvut.omo.vehicles.VehicleFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    // One iteration is one hour
    private static final int ITERATIONS = 24;

    private static final String CONFIGURATION = "houseConfig.json";

    public static void main(String[] args) {
        //House house = loadHouseFromJSON();
        House house = buildHouseFromBuilder();
        addCreatures(house.getAllRooms());
        if (house == null)
            System.out.println("Failed to load house from JSON");
        else {
            simulate(house);
        }
    }

    private static void simulate(House house) {
        Simulation sim = new Simulation(house);
        sim.turnOnAllDevices();
        for (int i = 0; i < ITERATIONS; i++) {
            sim.iterate(i);
        }
        sim.report();
    }

    private static House loadHouseFromJSON() {
        try {
            return House.loadFromJson(Main.CONFIGURATION);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static House buildHouseFromBuilder() {
        HouseBuilder houseBuilder = new HouseBuilder();
        FloorBuilder floorBuilder = new FloorBuilder(0);
        FloorBuilder floorBuilder1 = new FloorBuilder(1);
        RoomBuilder roomBuilderLivingR = new RoomBuilder("Living room", 22, 2);
        RoomBuilder roomBuilderLivingR2 = new RoomBuilder("Living room", 22, 2);
        RoomBuilder roomBuilderKitchen = new RoomBuilder("Kitchen", 21, 3);
        RoomBuilder roomBuilderBedroom = new RoomBuilder("Bedroom", 19, 1);

        House house = houseBuilder.addFloor(
                        floorBuilder.addRoom(
                                        roomBuilderLivingR.addAppliance(new TelevisionImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                                .addAppliance(new PlaystationImpl(Arrays.asList(80.0, 0.0, 0.0)))
                                                .addAppliance(new LightingImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                                .addAppliance(new TreadmillImpl(Arrays.asList(60.0, 0.0, 0.0)))
                                                .build())
                                .addRoom(
                                        new RoomBuilder("Garage", 15, 5)
                                                .addWindow(new WindowBuilder().addBlinds().build())
                                                .addVehicle(VehicleFactory.getInstance().createCar("Skoda"))
                                                .addVehicle(VehicleFactory.getInstance().createBike("Detske"))
                                                .addVehicle(VehicleFactory.getInstance().createBike("Damske"))
                                                .addVehicle(VehicleFactory.getInstance().createSki("Lyze"))
                                                .addAppliance(new TreadmillImpl(Arrays.asList(60.0, 0.0, 0.0)))
                                                .build())
                                .addDoor("Garage", "Living room")
                                .addRoom(new Room("Outside", 0, 100))
                                .addDoor("Garage", "Outside")
                                .build())
                .addFloor(
                        floorBuilder1.addRoom(
                                        roomBuilderKitchen.addAppliance(new LightingImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .addAppliance(new FridgeImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                                .addAppliance(new WashingMachineImpl(Arrays.asList(100.0, 100.0, 0.0)))
                                                .addAppliance(new CoffeeMachineImpl(Arrays.asList(20.0, 20.0, 0.0)))
                                                .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                                .build())
                                .addRoom(
                                        roomBuilderLivingR2.addAppliance(new LightingImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .addAppliance(new FridgeImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                                .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                                .addAppliance(new TreadmillImpl(Arrays.asList(60.0, 0.0, 0.0)))
                                                .build())
                                .addRoom(
                                        roomBuilderBedroom.addAppliance(new LightingImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                                .addAppliance(new TelevisionImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                                .addAppliance(new PlaystationImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .addAppliance(new CoffeeMachineImpl(Arrays.asList(20.0, 20.0, 0.0)))
                                                .build())
                                .addDoor("Kitchen", "Living room")
                                .addRoom(new Room("Outside", 0, 100))
                                .addDoor("Bedroom", "Living room")
                                .build()
                )
                .build();

        return house;
    }

    private static void addCreatures(List<Room> rooms) {
        PersonFactory personFactory = PersonFactory.getInstance();
        AnimalFactory animalFactory = AnimalFactory.getInstance();
        Collections.shuffle(rooms);
        for (int i = 0; i < rooms.size(); i++) {
            Person person;
            if (i == 0)
                person = personFactory.createBaby();
            else if (i == 1)
                person = personFactory.createChild();
            else if (i % 2 == 0)
                person = personFactory.createAdult();
            else
                person = personFactory.createElder();
            person.setFirstName("Firstname_ " + i);
            person.setLastName("Surname_ " + i);
            rooms.get(i).addCreature(person);
        }
        for (int i = 0; i < 3; i++) {
            Animal animal;
            if (i == 0)
                animal = animalFactory.createCat();
            else if (i == 1)
                animal = animalFactory.createParrot();
            else if (i % 2 == 0)
                animal = animalFactory.createDog();
            else
                animal = animalFactory.createSnake();
            rooms.get(i).addCreature(animal);
        }
    }
}