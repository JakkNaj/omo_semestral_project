package cz.fel.cvut.omo.appliances.fridge;

import cz.fel.cvut.omo.appliances.ApplianceContext;

import java.util.List;

public interface Fridge extends ApplianceContext {
    List<FridgeItem> getItems();

    void putItem(FridgeItem fridgeItem);

    void takeItem(FridgeItem fridgeItem);
}
