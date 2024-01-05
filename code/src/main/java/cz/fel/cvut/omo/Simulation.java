package cz.fel.cvut.omo;

import cz.fel.cvut.omo.activities.*;
import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.creature.person.Person;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.house.Room;
import cz.fel.cvut.omo.objectPool.ResourcePool;
import cz.fel.cvut.omo.report.ReportVisitorImpl;
import cz.fel.cvut.omo.vehicles.Vehicle;

import java.util.*;

/**
 * Simulation class
 * - 1 iteration = 1 hour in real time
 */
public class Simulation {

    private House house;

    private ReportVisitorImpl reportVisitor;

    private ResourcePool<Appliance> appliancePool;

    private ResourcePool<Vehicle> vehiclePool;

    private ResourcePool<Creature> creaturePool;

    private List<Activity> activities = new ArrayList<>();

    private Random rand = new Random();

    public Simulation(House house) {
        this.house = house;
        reportVisitor = new ReportVisitorImpl();
        creaturePool = new ResourcePool<>(House.getInstance().getAllCreatures());
        appliancePool = new ResourcePool<>(House.getInstance().getAllAppliances());
        vehiclePool = new ResourcePool<>(House.getInstance().getAllVehicles());
    }

    /**
     * Simulate one iteration of the simulation
     * @param i - current iteration
     */
    public void iterate(int i) {
        iterateAppliances(i);
        iterateRooms();
        iterateActivities();
        finishActivities(i);
        simulateBehavior(i);
    }

    public void report() {
        getConsumptionReport();
        getActivityAndUsageReport();
    }

    private void iterateAppliances(int i) {
        wearOffAppliances(i);
        saveApplianceConsumptions();
    }

    /**
     * Wear off appliances
     * - the appliance can be broken this way
     * @param i - current tick
     */
    private void wearOffAppliances(int i) {
        //wear off appliances every day (24th tick)
        if (i % 24 == 0)
            house.getFloors().forEach(floor -> floor.getRooms()
                    .forEach(room -> room.getAppliances()
                            .forEach(Appliance::wearOff)));
    }

    /**
     * Save appliance consumption too statistics
     */
    private void saveApplianceConsumptions() {
        //save consumption every tick (hour)
        house.getFloors().forEach(floor -> floor.getRooms()
                .forEach(room -> room.getAppliances()
                        .forEach(Appliance::saveConsumption)));
    }

    /**
     * Iterate rooms
     * - change temperature in every room if the Heater is ON
     */
    private void iterateRooms() {
        house.getFloors().forEach(floor -> floor.getRooms().forEach(Room::changeTemperature));
    }

    /**
     * Iterate activities
     * - every activity has its own iteration method
     */
    private void iterateActivities() {
        activities.forEach(Activity::iterate);
    }

    /**
     * Take care of activities that are finished
     * - if the activity is fix and the appliance/vehicle isn't fixable, buy new appliance/vehicle and replace the old one
     * - end of activity -> make the appliance or vehicle available and make the creature available too
     * - if the creature finished activity after active time, make it sleep
     * @param i - current tick
     */
    private void finishActivities(int i) {
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.isFinished()) {
                if (activity instanceof Fix) {
                    buyNewAppliance((Fix) activity);
                } else if (activity instanceof ApplianceActivity) {
                    appliancePool.makeAvailable(((ApplianceActivity) activity).getAppliance());
                } else if (activity instanceof VehicleActivity) {
                    vehiclePool.makeAvailable(((VehicleActivity) activity).getVehicle());
                }
                creaturePool.makeAvailable(activity.getCreature());
                if (!activeTimeForCreature(i)){
                    activity.getCreature().sleep();
                }
                iterator.remove();
            }
        }
    }

    /**
     * Buy new appliance and replace the old one
     * @param fix - fix activity
     */
    private void buyNewAppliance(Fix fix) {
        if (!fix.getFixable()) {
            Appliance newAppliance = fix.getNewAppliance();
            house.getFloors().forEach(floor -> floor.getRooms().forEach(room -> {
                if (room.containsAppliance(fix.getAppliance())) {
                    room.removeAppliance(fix.getAppliance());
                    appliancePool.discard(fix.getAppliance());
                    room.addAppliance(newAppliance);
                    appliancePool.insert(newAppliance);
                }
            }));
        }
    }

    /**
     * Simulate behavior of creatures
     * @param tick - current tick
     */
    public void simulateBehavior(int tick){
        if (activeTimeForCreature(tick)){
            House.getInstance().getAllCreatures().forEach(creature -> creature.setSleeping(false));
            doTodoActivities();
            createActivities();
            generateRandomEvent();
        }
    }

    /**
     * Create activities for creatures
     * - method trying to simulate the need of creature to use an appliance or vehicle
     * - if they don't want to use an appliance and all vehicles are used, make them wait and wonder around the house
     */
    private void createActivities() {
        while (creaturePool.hasAvailable()) {
            Creature creature = creaturePool.useRandom();
            Room creatureRoom = House.getInstance().getCreatureRoom(creature);
            if (rand.nextBoolean() && appliancePool.hasAvailable()) {
                Appliance appliance = appliancePool.useRandom();
                Room ApplianceRoom = House.getInstance().getApplianceRoom(appliance);
                activities.add(new ApplianceActivity(creature, appliance, rand.nextInt(1, 4)));
                if (ApplianceRoom != creatureRoom){
                    creatureRoom.removeCreature(creature);
                    ApplianceRoom.addCreature(creature);
                }
            } else if (vehiclePool.hasAvailable()) {
                Vehicle vehicle = vehiclePool.useRandom();
                Room VehicleRoom = House.getInstance().getVehicleRoom(vehicle);
                activities.add(new VehicleActivity(creature, vehicle, rand.nextInt(1, 4)));
                if (VehicleRoom != creatureRoom){
                    creatureRoom.removeCreature(creature);
                    VehicleRoom.addCreature(creature);
                }
            } else {
                activities.add(new WaitingActivity(creature));
            }
        }
    }

    /**
     * Get all creatures and check if they have any todo activities
     * - if they do, remove them from the creature pool and add them to the activities list
     */
    private void doTodoActivities() {
        Iterator<Creature> iterator = creaturePool.getAvailable().iterator();
        while (iterator.hasNext()) {
            Creature creature = iterator.next();
            if (creature instanceof Person person) {
                Activity activity = person.getFirstTodoActivity();
                if (activity != null) {
                    iterator.remove();  // Remove the current element
                    creaturePool.useResource(creature);
                    activities.add(activity);
                }
            }
        }
    }

    /**
     * Generate random event for creatures using appliances or vehicles
     * - 1/5 chance of event
     * - the event is breaking down the appliance or vehicle
     */
    private void generateRandomEvent() {
        if (rand.nextInt(5) == 0) {
            if (!activities.isEmpty()){
                Activity activity = activities.get(rand.nextInt(activities.size()));
                if (activity instanceof VehicleActivity vehicleActivity) {
                    activity.getCreature().generateEvent(House.getInstance().getVehicleRoom(vehicleActivity.getVehicle()), vehicleActivity.getVehicle());
                } else if(activity instanceof ApplianceActivity applianceActivity){
                    activity.getCreature().generateEvent(House.getInstance().getApplianceRoom(applianceActivity.getAppliance()), applianceActivity.getAppliance());
                }
                activities.remove(activity);
            }
        }
    }

    /**
     * @param tick - current tick
     * @return true if it's active time for creatures (8:00 - 20:00)
     */
    private boolean activeTimeForCreature(int tick) {
        int begin = 8;
        int end = 20;
        int hourInSimulation = tick % 24;
        return hourInSimulation >= begin && hourInSimulation <= end;
    }

    private void getConsumptionReport() {
        house.accept(reportVisitor);
    }

    private void getActivityAndUsageReport() {
        ApplianceActivity.printStatistics();
        VehicleActivity.printStatistics();
        WaitingActivity.printStatistics();
    }

    /**
     * Turn on all devices in the house
     * - for testing purposes
     */
    public void turnOnAllDevices() {
        house.getFloors().forEach(floor -> floor.getRooms()
                .forEach(room -> room.getAppliances()
                        .forEach(Appliance::turnOn)));
    }


}
