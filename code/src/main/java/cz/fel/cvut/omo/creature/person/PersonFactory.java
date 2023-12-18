package cz.fel.cvut.omo.creature.person;

public class PersonFactory {

    private static PersonFactory INSTANCE = null;

    private PersonFactory(){}

    public static PersonFactory getInstance() {
        if (INSTANCE == null) {
           INSTANCE = new PersonFactory();
        }
        return INSTANCE;
    }

    public Person createPerson() {
        return new Person();
    }
}
