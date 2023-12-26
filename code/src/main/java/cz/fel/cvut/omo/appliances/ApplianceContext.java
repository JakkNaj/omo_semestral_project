package cz.fel.cvut.omo.appliances;

import cz.fel.cvut.omo.appliances.states.ApplianceState;

import java.util.List;

public interface ApplianceContext {
    void setState(ApplianceState state);
    ApplianceState getState();
    List<Double> getConsumption();
}
