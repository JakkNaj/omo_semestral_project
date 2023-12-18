package cz.fel.cvut.omo.creature;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class for all living beings inside the simulation
 */
@Getter
@Setter
public abstract class Creature implements CreatureInterface {

    private boolean alive = true;

    private int age = 0;

    @Override
    public void age() {
        age++;
    }

    @Override
    public void die() {
        alive = false;
    }
}
