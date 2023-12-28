package cz.fel.cvut.omo.appliances.heater;

import java.util.List;
import cz.fel.cvut.omo.appliances.Appliance;

public class HeaterImpl extends Appliance implements Heater  {

    public HeaterImpl(List<Double> consumption) {
        super(consumption, 1240);
    }

    @Override
    public String getType() {
        return "Heater";
    }
}
