package cz.fel.cvut.omo.appliances.heater;

import java.io.IOException;
import java.util.List;
import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.events.Event;
import cz.fel.cvut.omo.events.HighTemperatureEvent;
import cz.fel.cvut.omo.events.LowTemperatureEvent;
import cz.fel.cvut.omo.observer.Observer;

public class HeaterImpl extends Appliance implements Heater, Observer {

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

    @Override
    public void update(Event event) throws IOException {
        reactToEvent(event);
        logEvent(event);
    }

    @Override
    public void reactToEvent(Event event) {
        if (event instanceof LowTemperatureEvent){
            this.turnOn();
        } else if (event instanceof HighTemperatureEvent){
            this.turnOff();
        }
    }

    @Override
    public void logEvent(Event event) throws IOException {
        //todo
    }
}
