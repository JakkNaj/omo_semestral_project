package cz.fel.cvut.omo;

import cz.fel.cvut.omo.house.House;

public class Main {

    private static final int ITERATIONS = 100;

    private static final String CONFIGURATION = "houseConfig.json";

    public static void main(String[] args) {
        House house = loadHouseFromJSON();
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
}