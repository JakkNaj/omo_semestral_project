package cz.fel.cvut.omo.events;

public class HighTemperatureEvent extends Event {

    public HighTemperatureEvent(String source, String cause) {
        super(source, cause);
    }

    @Override
    public String toString() {
        return "HighTemperatureEvent{" +
                "where='" + where + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
