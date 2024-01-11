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

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Main class of the project
 *
 * @author Jakub Najman (najmajak), Matěj Martínek (...)
 */
public class Main {

    // One iteration is one hour
    private static final int ITERATIONS = 24 * 10;
    private static final int run = 2;
    private static final boolean save = true;
    private static final String CONFIGURATION = "houseConfig01.json";

    public static void main(String[] args) throws IOException {


        File file = new File("HeaterLog.txt");
        file.delete();
        file = new File("PersonLog.txt");
        file.delete();

        House house;
        switch (run) {
            case 1:
                house = buildHouseFromBuilder2();
                addCreatures2(house.getAllRooms());
                break;
            case 2:
                house = House.loadFromJson("houseConfig01.json");
                break;
            case 3:
                house = House.loadFromJson("houseConfig02.json");
                break;
            case 0:
            default:
                house = buildHouseFromBuilder();
                addCreatures(house.getAllRooms());
                break;
        }
        if (house == null)
            System.out.println("Failed to load house from JSON");
        else {
            simulate(house);
        }

        if (save) {
            if (run == 3 || run == 1) {
                house.serializeToJson("houseConfig02.json");
            } else {
                house.serializeToJson("houseConfig01.json");
            }
        }
    }

    private static void simulate(House house) {
        Simulation sim = new Simulation(house);
        for (int i = 0; i < ITERATIONS; i++) {
            sim.iterate(i);
        }
        sim.report();
    }

    private static House buildHouseFromBuilder() {
        HouseBuilder houseBuilder = new HouseBuilder();
        FloorBuilder floorBuilder = new FloorBuilder(0);
        FloorBuilder floorBuilder1 = new FloorBuilder(1);
        RoomBuilder roomBuilderLivingR = new RoomBuilder("Living room", 22, 2);
        RoomBuilder roomBuilderLivingR2 = new RoomBuilder("Living room2", 22, 2);
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
                                .addDoor("Living room", "Outside")
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
                                .addDoor("Kitchen", "Living room2")
                                .addDoor("Bedroom", "Living room2")
                                .addDoor("Bedroom", "Kitchen")
                                .build()
                )
                .build();

        return house;
    }

    private static House buildHouseFromBuilder2() {
        HouseBuilder houseBuilder = new HouseBuilder();
        FloorBuilder floorBuilder = new FloorBuilder(0);
        FloorBuilder floorBuilder1 = new FloorBuilder(1);
        RoomBuilder roomBuilderLivingR = new RoomBuilder("Living room", 24, 2);
        RoomBuilder roomBuilderBath = new RoomBuilder("Bathroom", 20, 2);
        RoomBuilder roomBuilderHall = new RoomBuilder("Hallway", 19, 4);
        RoomBuilder roomBuilderMovie = new RoomBuilder("Movie room", 19, 4);
        RoomBuilder roomBuilderKitchen = new RoomBuilder("Kitchen", 23, 3);
        RoomBuilder roomBuilderBedroom = new RoomBuilder("Bedroom", 21, 1);

        House house = houseBuilder.addFloor(
                        floorBuilder.addRoom(
                                        roomBuilderLivingR.addAppliance(new TelevisionImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                                .addAppliance(new PlaystationImpl(Arrays.asList(80.0, 0.0, 0.0)))
                                                .addAppliance(new LightingImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                                .addAppliance(new TreadmillImpl(Arrays.asList(60.0, 0.0, 0.0)))
                                                .addAppliance(new CoffeeMachineImpl(Arrays.asList(20.0, 20.0, 0.0)))
                                                .build())
                                .addRoom(
                                        new RoomBuilder("Garage", 15, 5)
                                                .addWindow(new WindowBuilder().addBlinds().build())
                                                .addVehicle(VehicleFactory.getInstance().createCar("Mustang"))
                                                .addVehicle(VehicleFactory.getInstance().createCar("BMW"))
                                                .addVehicle(VehicleFactory.getInstance().createCar("VW"))
                                                .addVehicle(VehicleFactory.getInstance().createBike("Detske"))
                                                .addVehicle(VehicleFactory.getInstance().createBike("Damske"))
                                                .addVehicle(VehicleFactory.getInstance().createBike("Panske"))
                                                .addVehicle(VehicleFactory.getInstance().createSki("Lyze"))
                                                .addVehicle(VehicleFactory.getInstance().createSki("Snowboard"))
                                                .addVehicle(VehicleFactory.getInstance().createSki("Detske"))
                                                .addAppliance(new TreadmillImpl(Arrays.asList(60.0, 0.0, 0.0)))
                                                .build())
                                .addDoor("Garage", "Living room")
                                .addRoom(new Room("Garden", 0, 100))
                                .addDoor("Garage", "Garden")
                                .addRoom(
                                        roomBuilderMovie.addAppliance(new LightingImpl(Arrays.asList(25.0, 0.0, 0.0)))
                                                .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                                .addAppliance(new TelevisionImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                                .addAppliance(new PlaystationImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .build())
                                .addDoor("Living room", "Movie room")
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
                                        roomBuilderBath.addAppliance(new LightingImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .addAppliance(new FridgeImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                                .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                                .addAppliance(new TreadmillImpl(Arrays.asList(60.0, 0.0, 0.0)))
                                                .build())
                                .addRoom(
                                        roomBuilderBedroom.addAppliance(new LightingImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                                .addAppliance(new TelevisionImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                                .addAppliance(new PlaystationImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                                .build())
                                .addRoom(
                                        roomBuilderHall.addAppliance(new LightingImpl(Arrays.asList(25.0, 0.0, 0.0)))
                                                .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                                .build())
                                .addDoor("Kitchen", "Hallway")
                                .addDoor("Bedroom", "Hallway")
                                .addDoor("Bathroom", "Hallway")
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
            person.setFirstName("name_" + i);
            person.setLastName("lastname_" + i);
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
            animal.setName("pet_" + i);
            rooms.get(i).addCreature(animal);
        }
    }

    private static void addCreatures2(List<Room> rooms) {
        PersonFactory personFactory = PersonFactory.getInstance();
        AnimalFactory animalFactory = AnimalFactory.getInstance();
        Collections.shuffle(rooms);
        for (int i = 0; i < rooms.size() * 2; i++) {
            Person person;
            if (i == 0)
                person = personFactory.createBaby();
            else if (i == 1 || i == 3)
                person = personFactory.createChild();
            else if (i % 2 == 0)
                person = personFactory.createAdult();
            else
                person = personFactory.createElder();
            person.setFirstName("name_" + i);
            person.setLastName("lastname_" + i);
            rooms.get(i % rooms.size()).addCreature(person);
        }
        for (int i = 0; i < 10; i++) {
            Animal animal;
            if (i == 0)
                animal = animalFactory.createParrot();
            else if (i == 1 || i == 3)
                animal = animalFactory.createCat();
            else if (i % 2 == 0)
                animal = animalFactory.createDog();
            else
                animal = animalFactory.createSnake();
            animal.setName("pet_" + i);
            rooms.get(i% rooms.size()).addCreature(animal);
        }
    }
}