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

    public Animal createSnake(){
        Animal snake = new Animal();
        snake.setType(AnimalType.SNAKE);
        return snake;
    }

    public Animal createCat(){
        Animal cat = new Animal();
        cat.setType(AnimalType.CAT);
        return cat;
    }

    public Animal createDog(){
        Animal dog = new Animal();
        dog.setType(AnimalType.DOG);
        return dog;
    }

    public Animal createParrot(){
        Animal parrot = new Animal();
        parrot.setType(AnimalType.PARROT);
        return parrot;
    }
}

