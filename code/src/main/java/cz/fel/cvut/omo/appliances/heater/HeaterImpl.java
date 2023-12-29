package cz.fel.cvut.omo.appliances.heater;

import java.util.List;
import cz.fel.cvut.omo.appliances.Appliance;

public class HeaterImpl extends Appliance implements Heater  {

    private int setTemperature;

    public HeaterImpl(List<Double> consumption, int setTemperature) {
        super(consumption, 1240);
        this.setTemperature = setTemperature;
        actions.add(this::lowerTemperature);
        actions.add(this::upTemperature);
    }

    @Override
    public String getType() {
        return "Heater";
    }

    @Override
    public void lowerTemperature(){
        setTemperature--;
    }

    @Override
    public void upTemperature() {
        setTemperature++;
    }
}
