package cz.fel.cvut.omo.strategy;

import cz.fel.cvut.omo.house.House;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Strategy {

    private Strategy strategy;

    protected House house = House.getInstance();


    public Strategy() {
        strategy = new DayLight();
    }

    public void changeStrategy() {
        if (strategy instanceof DayLight) {
            strategy = new Night();
        } else {
            strategy = new DayLight();
        }
    }
}
