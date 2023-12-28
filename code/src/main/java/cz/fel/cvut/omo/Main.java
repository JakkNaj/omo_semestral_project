package cz.fel.cvut.omo;

import cz.fel.cvut.omo.appliances.coffeeMachine.CoffeeMachineImpl;
import cz.fel.cvut.omo.appliances.fridge.FridgeImpl;
import cz.fel.cvut.omo.appliances.heater.HeaterImpl;
import cz.fel.cvut.omo.appliances.light.LightingImpl;
import cz.fel.cvut.omo.appliances.tv.TelevisionImpl;
import cz.fel.cvut.omo.appliances.washingMachine.WashingMachineImpl;
import cz.fel.cvut.omo.builders.FloorBuilder;
import cz.fel.cvut.omo.builders.HouseBuilder;
import cz.fel.cvut.omo.builders.RoomBuilder;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.house.Room;

import java.util.Arrays;
import java.util.Collections;

public class Main {

    private static final int ITERATIONS = 100;

    private static final String CONFIGURATION = "houseConfig.json";

    public static void main(String[] args) {
        //House house = loadHouseFromJSON();
        House house = buildHouseFromBuilder();
        if (house == null)
            System.out.println("Failed to load house from JSON");
        else {
            simulate(house);
        }
    }

    private static void simulate(House house) {
        Simulation sim = new Simulation(house);
        for (int i = 0; i < ITERATIONS; i++) {
            sim.iterate();
            System.out.println();
            System.out.println();
        }
    }

    private static House loadHouseFromJSON(){
        try {
            return House.loadFromJson(Main.CONFIGURATION);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static House buildHouseFromBuilder(){
        HouseBuilder houseBuilder = new HouseBuilder();
        FloorBuilder floorBuilder = new FloorBuilder(0);
        FloorBuilder floorBuilder1 = new FloorBuilder(1);
        RoomBuilder roomBuilderLivingR = new RoomBuilder("Living room");
        RoomBuilder roomBuilderKitchen = new RoomBuilder("Kitchen");

        return houseBuilder.addFloor(
                floorBuilder.addRoom(
                            roomBuilderLivingR.addAppliance(new TelevisionImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                    .addAppliance(new LightingImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                    .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                    .build())
                            .build())
                    .addFloor(
                            floorBuilder1.addRoom(
                                    roomBuilderKitchen.addAppliance(new LightingImpl(Arrays.asList(50.0, 0.0, 0.0)))
                                            .addAppliance(new FridgeImpl(Arrays.asList(100.0, 0.0, 0.0)))
                                            .addAppliance(new WashingMachineImpl(Arrays.asList(100.0, 100.0, 0.0)))
                                            .addAppliance(new CoffeeMachineImpl(Arrays.asList(20.0, 20.0, 0.0)))
                                            .addAppliance(new HeaterImpl(Arrays.asList(50.0, 0.0, 50.0)))
                                            .build())
                                    .build()
                                    )
                    .build();
    }
}