package cz.fel.cvut.omo.vehicles;

import cz.fel.cvut.omo.house.House;
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

    @Getter
    @Setter
    protected boolean broken;

    public Vehicle(String name, int wheals, int capacity) {
        this.name = name;
        this.wheals = wheals;
        this.capacity = capacity;
        inUse = false;
        broken = false;
    }

    public void take(){
        this.inUse = true;
    }

    public void store(){
        this.inUse = false;
    }

    @Override
    public String toString() {
        return getType();
    }

    public String getType(){
        return "generic vehicle";
    }
}
