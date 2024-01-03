package cz.fel.cvut.omo.vehicles;

import cz.fel.cvut.omo.creature.person.Person;
import cz.fel.cvut.omo.creature.person.PersonFactory;
import cz.fel.cvut.omo.creature.person.PersonType;

public class VehicleFactory {

    private static VehicleFactory INSTANCE = null;

    private VehicleFactory(){}

    public static VehicleFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VehicleFactory();
        }
        return INSTANCE;
    }

    public Vehicle createCar(String name) {
        return new Car(name);
    }

    public Vehicle createSki(String name) {
        return new Ski(name);
    }

    public Vehicle createBike(String name) {
        return new Bicycle(name);
    }
}
