package cz.fel.cvut.omo.appliances.treadmill;

import cz.fel.cvut.omo.appliances.Appliance;

import java.util.List;

public class TreadmillImpl extends Appliance implements Treadmill{

    public TreadmillImpl(List<Double> consumption) {
        super(consumption, 180 * 24);
    }

    @Override
    public String getType() {
        return "Treadmill";
    }
}
