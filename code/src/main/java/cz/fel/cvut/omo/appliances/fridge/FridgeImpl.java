package cz.fel.cvut.omo.appliances.fridge;

import cz.fel.cvut.omo.appliances.Appliance;

import java.util.List;

public class FridgeImpl extends Appliance implements Fridge{

    private List<FridgeItem> items;
    public FridgeImpl(List<Double> consumption) {
        super(consumption, 1860);
    }

    @Override
    public List<FridgeItem> getItems() {
        return items;
    }

    @Override
    public void putItem(FridgeItem fridgeItem) {
        items.add(fridgeItem);
    }

    @Override
    public void takeItem(FridgeItem fridgeItem) {
        items.remove(fridgeItem);
    }

    @Override
    public String getType() {
        return "Fridge";
    }
}
