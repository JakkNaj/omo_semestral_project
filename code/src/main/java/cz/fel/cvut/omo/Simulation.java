package cz.fel.cvut.omo;

import cz.fel.cvut.omo.activities.Activity;
import cz.fel.cvut.omo.activities.ApplianceActivity;
import cz.fel.cvut.omo.activities.Fix;
import cz.fel.cvut.omo.activities.VehicleActivity;
import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.house.Room;
import cz.fel.cvut.omo.objectPool.ResourcePool;
import cz.fel.cvut.omo.report.ReportVisitorImpl;
import cz.fel.cvut.omo.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

//TODO fix for appliance and vehicle
public class Simulation {

    private House house;

    private ReportVisitorImpl reportVisitor;

    private ResourcePool<Appliance> appliancePool;

    private ResourcePool<Vehicle> vehiclePool;

    private ResourcePool<Creature> creaturePool;

    private List<Activity> activities = new ArrayList<>();

    public Simulation(House house) {
        this.house = house;
        reportVisitor = new ReportVisitorImpl();
    }

    public void iterate(int i){
        iterateAppliances(i);
        iterateRooms();
        iterateActivities();
        finishActivities();
    }

    public void report(){
        getConsumptionReport();
    }

    public void iterateAppliances(int i){
        wearOffAppliances(i);
        saveApplianceConsumptions();
    }

    public void wearOffAppliances(int i){
        //wear off appliances every day (24th tick)
        if (i % 24 == 0)
            house.getFloors().forEach(floor -> floor.getRooms()
                .forEach(room -> room.getAppliances()
                        .forEach(Appliance::wearOff)));
    }

    public void saveApplianceConsumptions(){
        //save consumption every tick (hour)
        house.getFloors().forEach(floor -> floor.getRooms()
                .forEach(room -> room.getAppliances()
                        .forEach(Appliance::saveConsumption)));
    }
    
    public void iterateRooms(){
        house.getFloors().forEach(floor -> floor.getRooms().forEach(Room::changeTemperature));
    }


    public void iterateActivities(){
        activities.forEach(Activity::iterate);
    }

    public void finishActivities(){
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.isFinished()) {
                if (activity instanceof Fix){
                    buyNewAppliance((Fix) activity);
                } else if(activity instanceof ApplianceActivity){
                    appliancePool.makeAvailable(((ApplianceActivity)activity).getAppliance());
                } else if(activity instanceof VehicleActivity){
                    vehiclePool.makeAvailable(((VehicleActivity)activity).getVehicle());
                }
                creaturePool.makeAvailable(activity.getCreature());
                iterator.remove();
            }
        }
    }

    public void buyNewAppliance(Fix fix){
        if (!fix.getFixable()){
            Appliance newAppliance = fix.getNewAppliance();
            house.getFloors().forEach(floor -> floor.getRooms().forEach(room -> {
                if (room.containsAppliance(fix.getAppliance())){
                    room.removeAppliance(fix.getAppliance());
                    appliancePool.discard(fix.getAppliance());
                    room.addAppliance(newAppliance);
                    appliancePool.insert(newAppliance);
                }
            }));
        }
    }

    public void getConsumptionReport(){
        house.accept(reportVisitor);
    }

    //todo delete later, only for testing purposes
    public void turnOnAllDevices(){
        house.getFloors().forEach(floor -> floor.getRooms()
                .forEach(room -> room.getAppliances()
                        .forEach(Appliance::turnOn)));
    }
}
