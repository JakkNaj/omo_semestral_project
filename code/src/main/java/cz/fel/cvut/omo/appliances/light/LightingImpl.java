package cz.fel.cvut.omo.appliances.light;

import cz.fel.cvut.omo.appliances.Appliance;

import java.util.List;

public class LightingImpl extends Appliance implements Lighting {

    public LightingImpl(List<Double> consumption) {
        super(consumption, 180);
    }
}
