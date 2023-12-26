package cz.fel.cvut.omo.appliances.states;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class OnState extends ApplianceState {
    private final double consumptionRatio = 1;
    @Override
    public List<Double> getConsumption(List<Double> consumption) {
        List<Double> outcome = new ArrayList<>();
        consumption.forEach(con -> outcome.add(con * consumptionRatio));
        return outcome;
    }
}
