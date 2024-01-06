package cz.fel.cvut.omo.events;

public class LowTemperatureEvent extends Event{

    public LowTemperatureEvent(String source, String cause) {
        super(source, cause);
    }

    @Override
    public String toString() {
        return "LowTemperatureEvent{" +
                "where='" + where + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
