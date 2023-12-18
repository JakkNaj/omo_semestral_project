package cz.fel.cvut.omo.creature;


/**
 * Interface for living beings
 */
public interface CreatureInterface {
    /**
     * makes being older
     */
    void age();
    /**
     * sets object's attribute alive to false,
     * making it dead to the simulation
     */
    void die();
}
