package cz.fel.cvut.omo.appliances.coffeeMachine;

public interface CoffeeMachine {
    double getCoffeeRatio();
    double getWaterRation();

    void makeEspresso();
    void makeLungo();
    void makeTurek();
}
