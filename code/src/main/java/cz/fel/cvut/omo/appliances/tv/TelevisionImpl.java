package cz.fel.cvut.omo.appliances.tv;

import cz.fel.cvut.omo.appliances.Appliance;

import java.util.List;

public class TelevisionImpl extends Appliance implements Television{
    public TelevisionImpl(List<Double> consumption) {
        super(consumption, 1240);
    }
}
