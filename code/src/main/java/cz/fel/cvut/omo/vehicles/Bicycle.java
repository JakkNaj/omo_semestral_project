package cz.fel.cvut.omo.vehicles;

public class Bicycle extends Vehicle{
    public Bicycle(String name) {
        super(name,2,1);
    }

    @Override
    public String getType() {
        return "bicycle";
    }
}
