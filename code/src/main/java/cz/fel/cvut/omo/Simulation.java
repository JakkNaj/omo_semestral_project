package cz.fel.cvut.omo;

import cz.fel.cvut.omo.activities.Activity;
import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.objectPool.ResourcePool;
import cz.fel.cvut.omo.report.ReportVisitorImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO fix for appliance and vehicle
public class Simulation {

    private House house;

    private ReportVisitorImpl reportVisitor;

    private ResourcePool<Appliance> appliancePool;

    private ResourcePool<Creature> creaturePool;

    private List<Activity> activities = new ArrayList<>();

    public Simulation(House house) {
        this.house = house;
        reportVisitor = new ReportVisitorImpl();
    }

    public void iterate(){
        wearOffAppliances();
        iterateActivities();
        finishActivities();
    }

    public void report(){
        getConsumptionReport();
    }

    public void wearOffAppliances(){
        house.getFloors().forEach(floor -> floor.getRooms()
                .forEach(room -> room.getAppliances()
                        .forEach(Appliance::wearOff)));
    }

    public void iterateActivities(){
        activities.forEach(Activity::iterate);
    }

    public void finishActivities(){
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.isFinished()) {
                appliancePool.makeAvailable(activity.getAppliance());
                creaturePool.makeAvailable(activity.getCreature());
                iterator.remove();
            }
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
