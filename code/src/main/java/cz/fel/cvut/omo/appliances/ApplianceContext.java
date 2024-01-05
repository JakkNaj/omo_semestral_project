package cz.fel.cvut.omo.appliances;

import cz.fel.cvut.omo.appliances.states.ApplianceState;

import java.util.List;

/**
 * Interface for all appliances
 */
public interface ApplianceContext {
    void setState(ApplianceState state);
    ApplianceState getState();
    List<Double> getCurrentConsumption();

    /**
     * Turns on the appliance
     */
    void turnOn();

    /**
     * Turns off the appliance
     */
    void turnOff();

    /**
     * Breaks the appliance
     */
    void turnBroken();

    /**
     * Repairs the appliance
     */
    void repair();

    /**
     * Turns appliance to idle state
     */
    void turnIdle();

    /**
     * Method for creatures to use this appliance
     * - uses get RandomAction() method
     */
    void use();

    /**
     * Method to get random runnable for creature to use
     */
    Runnable getRandomAction();
}
