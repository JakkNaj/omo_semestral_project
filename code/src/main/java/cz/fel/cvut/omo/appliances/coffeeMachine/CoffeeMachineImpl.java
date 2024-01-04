package cz.fel.cvut.omo.appliances.coffeeMachine;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.appliances.states.OnState;

import java.util.List;

public class CoffeeMachineImpl extends Appliance implements CoffeeMachine {

    private double coffeeRatio = 0;

    private double waterRatio = 0;

    private CoffeeMachineStates coffeeMachineState = CoffeeMachineStates.NEEDS_COFFEE;


    public CoffeeMachineImpl(List<Double> consumption) {
        super(consumption, 365 * 24);
        actions.add(this::makeEspresso);
        actions.add(this::makeTurek);
    }

    @Override
    public double getCoffeeRatio() {
        return coffeeRatio;
    }

    @Override
    public double getWaterRation() {
        return waterRatio;
    }

    @Override
    public boolean makeEspresso() {
        if (this.getState() instanceof OnState &&
            coffeeMachineState == CoffeeMachineStates.READY){
            if (coffeeRatio >= 0.1)
                coffeeRatio -= 0.1;
            else {
                coffeeMachineState = CoffeeMachineStates.NEEDS_COFFEE;
                return false;
            }
            if (waterRatio >= 0.05)
                waterRatio -= 0.05;
            else {
                coffeeMachineState = CoffeeMachineStates.NEEDS_WATER;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean makeTurek() {
        if (this.getState() instanceof OnState &&
            coffeeMachineState == CoffeeMachineStates.READY){
            if (coffeeRatio >= 0.1)
                coffeeRatio -= 0.1;
            else {
                coffeeMachineState = CoffeeMachineStates.NEEDS_COFFEE;
                return false;
            }
            if (waterRatio >= 0.1)
                waterRatio -= 0.1;
            else {
                coffeeMachineState = CoffeeMachineStates.NEEDS_WATER;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getType() {
        return "Coffee machine";
    }
}
