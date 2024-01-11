package cz.fel.cvut.omo.appliances.states;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class BrokenState extends ApplianceState {
    @Override
    public List<Double> getCurrentConsumption(List<Double> consumption) {
        List<Double> outcome = new ArrayList<>();
        this.setConsumptionRatio(0.2);
        consumption.forEach(con -> outcome.add(con * this.getConsumptionRatio()));
        return outcome;
    }

    @Override
    public String toString() {
        return "state: BROKEN";
    }
}
