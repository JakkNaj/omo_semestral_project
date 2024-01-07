package cz.fel.cvut.omo.house;

import cz.fel.cvut.omo.activities.Activity;
import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.heater.Heater;
import cz.fel.cvut.omo.appliances.states.ApplianceState;
import cz.fel.cvut.omo.appliances.states.OnState;
import cz.fel.cvut.omo.creature.Creature;
import cz.fel.cvut.omo.creature.person.Person;
import cz.fel.cvut.omo.events.Event;
import cz.fel.cvut.omo.events.HighTemperatureEvent;
import cz.fel.cvut.omo.events.LowTemperatureEvent;
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

    private int wantedTemperature;

    private int deltaOfTemperature;

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

    public Room(String name, int wantedTemperature, int deltaOfTemperature) {
        this.wantedTemperature = wantedTemperature;
        this.deltaOfTemperature = deltaOfTemperature;
        doors = new ArrayList<>();
        creatures = new ArrayList<>();
        windows = new ArrayList<>();
        appliances = new ArrayList<>();
        this.name = name;
        temperature = 18;
    }

    public void addDoor(Door door) {
        doors.add(door);
    }

    public void addWindow(Window window) {
        windows.add(window);
    }

    public boolean containsAppliance(Appliance appliance) {
        return appliances.contains(appliance);
    }

    public void addAppliance(Appliance appliance) {
        appliances.add(appliance);
        if (appliance instanceof Observer)
            // appliance Observer subscribe this, so the newly generated events can be observed
            subscribe((Observer) appliance);
    }

    public void removeAppliance(Appliance appliance) {
        appliances.remove(appliance);
        if (appliance instanceof Observer)
            unsubscribe((Observer) appliance);
    }

    public void accept(ReportVisitor reportVisitor) {
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
        if (temperature < wantedTemperature - deltaOfTemperature) {
            notifyAll(new LowTemperatureEvent(name, "too cold"));
        } else if (temperature > wantedTemperature + deltaOfTemperature) {
            notifyAll(new HighTemperatureEvent(name, "too hot"));
        }
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void tryToMoveCreatures() {
        for (Creature creature : creatures) {
            creature.move(this);
        }
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
        if (creature instanceof Person)
            unsubscribe((Person) creature);
    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
        if (creature instanceof Person)
            subscribe((Person) creature);
    }

    /**
     * if there is heater in room and is on, increase room temperature, else decrease room temperature
     */
    public void changeTemperature() {
        for (Appliance appliance : appliances) {
            if (appliance instanceof Heater heater) {
                if (heater.getState() instanceof OnState) {
                    temperature++;
                    return;
                }
            }
        }
        temperature--;
        //throw new IllegalAccessException("no heater in room");
    }

    /**
     * sets wanted temperature, and how much can actual temperature deviate from it
     *
     * @param wantedTemperature  temperature to be achieved
     * @param deltaOfTemperature temperature deviation
     */
    public void setTemperature(int wantedTemperature, int deltaOfTemperature) {
        this.wantedTemperature = wantedTemperature;
        this.deltaOfTemperature = deltaOfTemperature;
    }

    public void acceptEvent(Event event) {
        notifyAll(event);
    }

    @Override
    public String toString() {
        return "Room{" + this.getName() + "}";
    }
}
