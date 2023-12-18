package cz.fel.cvut.omo.creature.animal;

import cz.fel.cvut.omo.creature.person.Person;
import cz.fel.cvut.omo.creature.person.PersonFactory;

public class AnimalFactory {

    private static AnimalFactory INSTANCE = null;

    private AnimalFactory(){}

    public static AnimalFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AnimalFactory();
        }
        return INSTANCE;
    }

    public Animal createAnimal() {
        return new Animal();
    }
}

