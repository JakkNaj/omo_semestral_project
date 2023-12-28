package cz.fel.cvut.omo;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.house.House;
import cz.fel.cvut.omo.objectPool.ResourcePool;
import cz.fel.cvut.omo.report.ReportVisitorImpl;

public class Simulation {

    private House house;

    private ReportVisitorImpl reportVisitor;

    private ResourcePool<Appliance> appliancePool;

    private ResourcePool<Creature> creaturePool;

    public Simulation(House house) {
        this.house = house;
        reportVisitor = new ReportVisitorImpl();
    }

    public void iterate(){
        wearOffAppliances();
    }

    public void report(){
        getConsumptionReport();
    }

    public void wearOffAppliances(){
        house.getFloors().forEach(floor -> floor.getRooms()
                .forEach(room -> room.getAppliances()
                        .forEach(Appliance::wearOff)));
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
