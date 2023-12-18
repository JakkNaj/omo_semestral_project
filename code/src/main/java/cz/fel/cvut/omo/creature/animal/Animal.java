package cz.fel.cvut.omo.creature.animal;


import lombok.Getter;
import lombok.Setter;

/**
 * Class representing an Animal being inside the simulation
 */
@Getter
@Setter
public class Animal {

    private String name;

    private AnimalType type;
}
