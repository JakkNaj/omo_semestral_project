package cz.fel.cvut.omo.appliances.states;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public abstract class ApplianceState {

    private double consumptionRatio;

    public List<Double> getConsumption(List<Double> consumption){
        List<Double> outcome = new ArrayList<>();
        consumption.forEach(con -> outcome.add(con * consumptionRatio));
        return outcome;
    };
}
