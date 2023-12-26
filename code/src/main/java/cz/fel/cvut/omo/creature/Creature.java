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

    private boolean sleeping = false;

    @Override
    public void age() {
        age++;
    }

    @Override
    public void die() {
        alive = false;
    }

    public void sleep() {
        sleeping = true;
    }
}
