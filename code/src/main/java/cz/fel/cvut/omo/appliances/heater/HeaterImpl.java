package cz.fel.cvut.omo.appliances.heater;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.events.Event;
import cz.fel.cvut.omo.events.HighTemperatureEvent;
import cz.fel.cvut.omo.events.LowTemperatureEvent;
import cz.fel.cvut.omo.observer.Observer;

public class HeaterImpl extends Appliance implements Heater, Observer {

    public HeaterImpl(List<Double> consumption) {
        super(consumption, 365 * 8 * 24);
    }

    @Override
    public String getType() {
        return "Heater";
    }

    @Override
    public void update(Event event) throws IOException {
        reactToEvent(event);
        logEvent(event);
    }

    @Override
    public void reactToEvent(Event event) {
        if (event instanceof LowTemperatureEvent) {
            this.turnOn();
        } else if (event instanceof HighTemperatureEvent) {
            this.turnOff();
        }
    }

    @Override
    public void logEvent(Event event) throws IOException {
        File file = new File("HeaterLog.txt");
        file.createNewFile();
        FileWriter fw = new FileWriter(file.getName(), true);
        fw.write("Heater " + event.getWhere() + " accepted event with reason " + event.getReason() + "\n\r");
        fw.close();
        //System.out.println("heaterEvent");
    }
}
