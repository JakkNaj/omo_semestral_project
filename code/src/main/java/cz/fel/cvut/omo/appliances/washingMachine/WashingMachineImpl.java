package cz.fel.cvut.omo.appliances.washingMachine;

import cz.fel.cvut.omo.appliances.Appliance;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class WashingMachineImpl extends Appliance implements WashingMachine {

    @Setter
    private boolean loaded;

    public WashingMachineImpl(List<Double> consumption) {
        super(consumption, 360 * 8 * 24);
        configureRunnableActions();
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void load() {
        loaded = true;
    }

    public void wash() {
        loaded = false;
    }

    @Override
    public String getType() {
        return "Washing machine";
    }

    @Override
    public void configureRunnableActions() {
        actions = new ArrayList<>();
        actions.add(this::load);
        actions.add(this::wash);
    }
}
