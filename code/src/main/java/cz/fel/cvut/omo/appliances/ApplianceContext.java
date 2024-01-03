package cz.fel.cvut.omo.appliances;

import cz.fel.cvut.omo.appliances.states.ApplianceState;

import java.util.List;

public interface ApplianceContext {
    void setState(ApplianceState state);
    ApplianceState getState();
    List<Double> getCurrentConsumption();

    void turnOn();

    void turnOff();

    void turnBroken();

    void repair();

    void turnIdle();

    void use();

    Runnable getRandomAction();
}
