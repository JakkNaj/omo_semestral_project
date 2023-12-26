package cz.fel.cvut.omo.appliances.washingMachine;

import cz.fel.cvut.omo.appliances.Appliance;
import lombok.Setter;

import java.util.List;

public class WashingMachineImpl extends Appliance implements WashingMachine {

    @Setter
    private boolean loaded;

    public WashingMachineImpl(List<Double> consumption) {
        super(consumption, 360);
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

}
