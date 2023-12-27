package cz.fel.cvut.omo.appliances.washingMachine;

import cz.fel.cvut.omo.appliances.ApplianceContext;

public interface WashingMachine extends ApplianceContext {
    boolean isLoaded();

    void load();

    void wash();
}
