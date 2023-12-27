package cz.fel.cvut.omo.appliances.coffeeMachine;

public interface CoffeeMachine {
    double getCoffeeRatio();
    double getWaterRation();

    boolean makeEspresso();
    boolean makeTurek();
}
