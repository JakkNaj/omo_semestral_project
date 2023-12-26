package cz.fel.cvut.omo.appliances;


import cz.fel.cvut.omo.appliances.states.*;
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

    @Override
    public void turnOn() {
        this.setState(new OnState());
    }

    @Override
    public void turnOff() {
        this.setState(new OffState());
    }

    @Override
    public void turnBroken() {
        this.setState(new BrokenState());
    }

    @Override
    public void turnIdle() {
        this.setState(new IdleState());
    }

    //todo total consumption - nulled every month
}
