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

    public void iterateAppliances(int i) {
        wearOffAppliances(i);
        saveApplianceConsumptions();
    }

    public void wearOffAppliances(int i) {
        //wear off appliances every day (24th tick)
        if (i % 24 == 0)
            house.getFloors().forEach(floor -> floor.getRooms()
                    .forEach(room -> room.getAppliances()
                            .forEach(Appliance::wearOff)));
    }

    public void saveApplianceConsumptions() {
        //save consumption every tick (hour)
        house.getFloors().forEach(floor -> floor.getRooms()
                .forEach(room -> room.getAppliances()
                        .forEach(Appliance::saveConsumption)));
    }

    public void iterateRooms() {
        house.getFloors().forEach(floor -> floor.getRooms().forEach(Room::changeTemperature));
    }


    public void iterateActivities() {
        activities.forEach(Activity::iterate);
    }

    public void finishActivities(int i) {
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

    public void buyNewAppliance(Fix fix) {
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

    private void createActivities() {
        while (creaturePool.hasAvailable()) {
            Creature creature = creaturePool.useRandom();
            if (rand.nextBoolean() && appliancePool.hasAvailable()) {
                Appliance appliance = appliancePool.useRandom();
                if (House.getInstance().getApplianceRoom(appliance) == House.getInstance().getCreatureRoom(creature)) {
                    activities.add(new ApplianceActivity(creature, appliance, rand.nextInt(1, 4)));
                } else{
                    appliancePool.makeAvailable(appliance);
                    creaturePool.makeAvailable(creature);
                }

            } else if (vehiclePool.hasAvailable()) {
                Vehicle vehicle = vehiclePool.useRandom();
                if (House.getInstance().getVehicleRoom(vehicle) == House.getInstance().getCreatureRoom(creature)) {
                    activities.add(new VehicleActivity(creature, vehicle, rand.nextInt(1, 12)));
                } else {
                    vehiclePool.makeAvailable(vehicle);
                    creaturePool.makeAvailable(creature);
                }
            } else {
                activities.add(new WaitingActivity(creature));
            }
        }
    }

    private void createActivitiesNumTWO() {
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

    public void generateRandomEvent() {
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

    public void simulateBehavior(int tick){
        if (activeTimeForCreature(tick)){
            House.getInstance().getAllCreatures().forEach(creature -> creature.setSleeping(false));
            doTodoActivities();
            createActivitiesNumTWO();
            generateRandomEvent();
        }
    }

    private boolean activeTimeForCreature(int tick) {
        int begin = 8;
        int end = 20;
        int hourInSimulation = tick % 24;
        return hourInSimulation >= begin && hourInSimulation <= end;
    }

    public void getConsumptionReport() {
        house.accept(reportVisitor);
    }

    public void getActivityAndUsageReport() {
        ApplianceActivity.printStatistics();
        VehicleActivity.printStatistics();
        WaitingActivity.printStatistics();
    }

    //todo delete later, only for testing purposes
    public void turnOnAllDevices() {
        house.getFloors().forEach(floor -> floor.getRooms()
                .forEach(room -> room.getAppliances()
                        .forEach(Appliance::turnOn)));
    }


}
