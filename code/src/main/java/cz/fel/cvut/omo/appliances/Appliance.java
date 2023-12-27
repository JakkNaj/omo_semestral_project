package cz.fel.cvut.omo.appliances;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import cz.fel.cvut.omo.appliances.states.*;
import cz.fel.cvut.omo.report.ReportVisitor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class Appliance implements ApplianceContext {

    //todo down tick wearTear each day in simulation

    @Getter
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
        //don't turn on when appliance broken
        if (this.state instanceof BrokenState)
            return;
        this.setState(new OnState());
    }

    @Override
    public void turnOff() {
        //don't change state when broken
        if (this.state instanceof BrokenState)
            return;
        this.setState(new OffState());
    }

    //todo after appliance broken, some person has to repair it
    @Override
    public void turnBroken() {
        this.setState(new BrokenState());
    }

    @Override
    public void repair() {
        if (this.state instanceof BrokenState)
            this.setState(new IdleState());
    }

    @Override
    public void turnIdle() {
        //don't change state when broken
        if (this.state instanceof BrokenState)
            return;
        this.setState(new IdleState());
    }


    //use each day on appliance tick
    public void wearOff(){
        wearTear--;
        if (wearTear == 0)
            this.setState(new BrokenState());
    }

    //todo total consumption - nulled every month

    public void accept(ReportVisitor reportVisitor){
        reportVisitor.visit(this);
    }

    public String getType(){
        return "generic Appliance";
    }
}
