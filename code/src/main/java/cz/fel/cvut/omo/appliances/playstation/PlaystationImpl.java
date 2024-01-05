package cz.fel.cvut.omo.appliances.playstation;

import cz.fel.cvut.omo.appliances.Appliance;

import java.util.List;

public class PlaystationImpl extends Appliance implements Playstation {

    public PlaystationImpl(List<Double> consumption) {
        super(consumption, 365 * 24 * 8);
    }

    @Override
    public String getType() {
        return "Playstation";
    }
}
