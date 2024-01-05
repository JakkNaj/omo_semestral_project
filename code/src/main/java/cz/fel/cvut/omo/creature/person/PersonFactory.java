package cz.fel.cvut.omo.creature.person;

/**
 * Factory for creating people with different types
 */
public class PersonFactory {

    private static PersonFactory INSTANCE = null;

    private PersonFactory(){}

    public static PersonFactory getInstance() {
        if (INSTANCE == null) {
           INSTANCE = new PersonFactory();
        }
        return INSTANCE;
    }

    public Person createBaby() {
        return new Person(PersonType.BABY);
    }

    public Person createChild() {
        return new Person(PersonType.CHILD);
    }

    public Person createAdult() {
        return new Person(PersonType.ADULT);
    }

    public Person createElder() {
        return new Person(PersonType.ELDER);
    }
}
