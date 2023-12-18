package cz.fel.cvut.omo.creature.person;

import cz.fel.cvut.omo.creature.Creature;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing a Human being inside the simulation
 */
@Getter
@Setter
public class Person extends Creature {

     private String firstName;

     private String lastName;

     private PersonType type;
}
