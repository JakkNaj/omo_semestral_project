package cz.fel.cvut.omo.appliances.states;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ApplianceState {

    @Setter
    @Getter
    private double consumptionRatio;

    public List<Double> getCurrentConsumption(List<Double> consumption){
        return consumption.stream()
                .map(con -> con * consumptionRatio)
                .collect(Collectors.toList());
    };
}
