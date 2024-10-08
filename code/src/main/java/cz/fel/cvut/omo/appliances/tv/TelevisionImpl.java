package cz.fel.cvut.omo.appliances.tv;

import cz.fel.cvut.omo.appliances.Appliance;

import java.util.List;

public class TelevisionImpl extends Appliance implements Television{
    public TelevisionImpl(List<Double> consumption) {
        super(consumption, 1240 * 24);
    }

    @Override
    public String getType() {
        return "Television";
    }

    @Override
    public void turnOff() {
        super.turnIdle();
    }
}
