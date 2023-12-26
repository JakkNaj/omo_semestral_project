package cz.fel.cvut.omo.builders;

import cz.fel.cvut.omo.house.Floor;
import cz.fel.cvut.omo.house.House;

public class HouseBuilder {
    private final House house;

    public HouseBuilder() {
        house = House.getInstance();
    }

    public House build(){
        return house;
    }

    public HouseBuilder addFloor(Floor floor){
        house.addFloor(floor);
        return this;
    }
}
