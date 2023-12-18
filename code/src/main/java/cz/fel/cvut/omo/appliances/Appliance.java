package cz.fel.cvut.omo.appliances;


import cz.fel.cvut.omo.appliances.states.ApplianceState;
import cz.fel.cvut.omo.appliances.states.OffState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class Appliance implements ApplianceContext {

    private ApplianceState state;
    private final List<Double> consumption;

    public Appliance(List<Double> consumption) {
        this.consumption = consumption;
        this.state = new OffState();
    }

    @Override
    public void setState(ApplianceState state) {
        this.state = state;
    }

    @Override
    public ApplianceState getState() {
        return state;
    }

    @Override
    public List<Double> getConsumption() {
        return state.getConsumption(consumption);
    }

    //todo total consumption - nulled every month

}
