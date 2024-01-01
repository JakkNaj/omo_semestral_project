package cz.fel.cvut.omo.activities;

import cz.fel.cvut.omo.appliances.Appliance;
import cz.fel.cvut.omo.creature.Creature;

public class Fix extends ApplianceActivity{

    public Fix(Creature creature, Appliance appliance) {
        super(creature, appliance, 200);
    }
}
