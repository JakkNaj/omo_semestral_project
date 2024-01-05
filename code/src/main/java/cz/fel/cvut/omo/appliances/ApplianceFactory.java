package cz.fel.cvut.omo.appliances;

import cz.fel.cvut.omo.appliances.coffeeMachine.CoffeeMachineImpl;
import cz.fel.cvut.omo.appliances.fridge.FridgeImpl;
import cz.fel.cvut.omo.appliances.heater.HeaterImpl;
import cz.fel.cvut.omo.appliances.light.LightingImpl;
import cz.fel.cvut.omo.appliances.playstation.PlaystationImpl;
import cz.fel.cvut.omo.appliances.treadmill.TreadmillImpl;
import cz.fel.cvut.omo.appliances.tv.TelevisionImpl;
import cz.fel.cvut.omo.appliances.washingMachine.WashingMachineImpl;

import java.util.List;

public class ApplianceFactory {
    public static Appliance createAppliance(String type, List<Double> consumption) {
        switch (type) {
            case "TelevisionImpl":
                return new TelevisionImpl(consumption);
            case "LightImpl":
                return new LightingImpl(consumption);
            case "WashingMachineImpl":
                return new WashingMachineImpl(consumption);
            case "HeaterImpl":
                return new HeaterImpl(consumption);
            case "FridgeImpl":
                return new FridgeImpl(consumption);
            case "CoffeeMachineImpl":
                return new CoffeeMachineImpl(consumption);
            case "PlaystationImpl":
                return new PlaystationImpl(consumption);
            case "TreadmillImpl":
                return new TreadmillImpl(consumption);
            default:
                throw new IllegalArgumentException("Invalid appliance type: " + type);
        }
    }
}

