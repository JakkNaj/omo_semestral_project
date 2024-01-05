package cz.fel.cvut.omo.appliances.states;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ApplianceState {

    private double consumptionRatio;

    public List<Double> getCurrentConsumption(List<Double> consumption){
        return consumption.stream()
                .map(con -> con * consumptionRatio)
                .collect(Collectors.toList());
    };
}
