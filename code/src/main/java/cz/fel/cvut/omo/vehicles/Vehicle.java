package cz.fel.cvut.omo.vehicles;

import lombok.Getter;
import lombok.Setter;

public abstract class Vehicle {
    @Getter
    protected final String name;

    protected final int wheals;

    @Getter
    protected final int capacity;

    @Getter
    @Setter
    protected boolean inUse;

    public Vehicle(String name, int wheals, int capacity) {
        this.name = name;
        this.wheals = wheals;
        this.capacity = capacity;
        inUse = false;
    }
}
