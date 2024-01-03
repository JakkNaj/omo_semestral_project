package cz.fel.cvut.omo.house;

import cz.fel.cvut.omo.activities.Activity;
import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.creature.person.Person;
import cz.fel.cvut.omo.events.Event;
import cz.fel.cvut.omo.observer.Observable;
import cz.fel.cvut.omo.observer.Observer;
import cz.fel.cvut.omo.report.ReportVisitor;
import cz.fel.cvut.omo.vehicles.Vehicle;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing Room in simulation.
 */
public class Room implements Observable {

    @Getter
    private final String name;

    private int temperature;

    @Getter
    private final List<Creature> creatures;

    @Getter
    private final List<Door> doors;

    @Getter
    private final List<Window> windows;

    @Getter
    private final List<Appliance> appliances;

    private final List<Observer> observers = new ArrayList<>();

    @Getter
    private final List<Vehicle> vehicles = new ArrayList<>();

    public Room(String name) {
        doors = new ArrayList<>();
        creatures = new ArrayList<>();
        windows = new ArrayList<>();
        appliances = new ArrayList<>();
        this.name = name;
    }

    public void addDoor(Door door){
        doors.add(door);
    }

    public void addWindow(Window window){
        windows.add(window);
    }

    public boolean containsAppliance(Appliance appliance){
        return appliances.contains(appliance);
    }

    public void addAppliance(Appliance appliance){
        appliances.add(appliance);
        if (appliance instanceof Observer)
            // appliance Observer subscribe this, so the newly generated events can be observed
            subscribe((Observer) appliance);
    }

    public void removeAppliance(Appliance appliance){
        appliances.remove(appliance);
        if (appliance instanceof Observer)
            unsubscribe((Observer) appliance);
    }

    public void addPerson(Person person){
        creatures.add(person);
        //person subscribe this, so the newly generated events can be observed
        subscribe(person);
    }

    public void accept(ReportVisitor reportVisitor){
        reportVisitor.visit(this);
        appliances.forEach(appliance -> {
            appliance.accept(reportVisitor);
        });
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAll(Event event) {
        observers.forEach(observer -> {
            try {
                observer.update(event);
            } catch (Exception e) {
                // todo maybe logging??
                e.printStackTrace();
            }
        });
    }

    public void generateEvent() {
        // todo - call notifyAll() with generated event
    }

    public void removeVehicle(Vehicle vehicle){
        vehicles.remove(vehicle);
    }

    public void useVehicle(Vehicle vehicle){
        removeVehicle(vehicle);
    }

    public void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    public void storeVehicle(Vehicle vehicle){
        addVehicle(vehicle);
    }

    public void tryToMoveCreatures(){
        for(Creature creature : creatures){
            creature.move(this);
        }
    }

    public void removeCreature(Creature creature){
        creatures.remove(creature);
    }

    public void addCreature(Creature creature){
        creatures.add(creature);
    }
}
