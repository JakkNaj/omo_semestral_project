package cz.fel.cvut.omo.house;

import lombok.Getter;

import static java.time.LocalTime.now;

public class Window {
    @Getter
    private final int id;

    private Blinds blinds;

    public Window() {
        id = now().toSecondOfDay();
        blinds = null;
    }

    public void addBlinds() {
        if (!hasBlinds()) {
            blinds = new Blinds();
            return;
        }
        throw new IllegalStateException("window already has blinds");
    }

    private boolean hasBlinds() {
        return blinds != null;
    }
}
