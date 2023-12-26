package cz.fel.cvut.omo.appliances.coffeeMachine;

import cz.fel.cvut.omo.appliances.Appliance;

import java.util.List;

public class CoffeeMachineImpl extends Appliance implements CoffeeMachine {

    private double coffeeRatio;

    private double waterRatio;

    public CoffeeMachineImpl(List<Double> consumption) {
        super(consumption, 620);
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
    public void makeEspresso() {

    }

    @Override
    public void makeLungo() {

    }

    @Override
    public void makeTurek() {

    }
}
