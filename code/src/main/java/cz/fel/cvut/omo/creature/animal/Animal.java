package cz.fel.cvut.omo.creature.animal;


import cz.fel.cvut.omo.creature.Creature;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing an Animal being inside the simulation
 */
@Getter
@Setter
public class Animal extends Creature {

    private String name;

    private AnimalType type;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
