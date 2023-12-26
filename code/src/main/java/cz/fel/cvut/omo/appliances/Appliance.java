package cz.fel.cvut.omo.appliances;


import cz.fel.cvut.omo.appliances.states.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class Appliance implements ApplianceContext {

    //todo down tick wearTear each day in simulation
    private int wearTear;
    private ApplianceState state;
    private final List<Double> consumption;

    public Appliance(List<Double> consumption, int wearTear) {
        this.consumption = consumption;
        this.wearTear = wearTear;
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


    //use each day on appliance tick
    public void wearOff(){
        wearTear--;
        if (wearTear == 0)
            this.setState(new BrokenState());
    }

    //todo total consumption - nulled every month
}
