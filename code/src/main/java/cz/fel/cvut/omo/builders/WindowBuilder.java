package cz.fel.cvut.omo.builders;

import cz.fel.cvut.omo.house.Window;

public class WindowBuilder {
    private final Window window = new Window();

    public Window build(){
        return window;
    }

    public WindowBuilder addBlinds(){
        window.addBlinds();
        return this;
    }
}
