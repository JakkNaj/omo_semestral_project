package cz.fel.cvut.omo.objectPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class implementing generic ResourcePool
 * @param <T> type of resource
 */
public class ResourcePool<T> {

    private final List<T> inUse = new ArrayList<>();
    private final List<T> available = new ArrayList<>();

    public ResourcePool(List<T> resources) {
        available.addAll(resources);
    }

    /**
     * Inserts new resource to resource pool
     * @param resource resource to be inserted
     */
    public void insert(T resource) {
        available.add(resource);
    }

    /**
     * Discards resource from resource pool if not in use
     * @param resource resource to be discarded
     */
    public void discard(T resource) {
        available.remove(resource);
    }

    /**
     * @return true if resource pool has available resources
     */
    public boolean hasAvailable() {
        return !available.isEmpty();
    }

    /**
     * @param resource to check, if it's in use
     * @return true if resource is in use
     */
    public boolean isInUse(T resource) {
        return inUse.contains(resource);
    }

    /**
     * @return first available resource from resource pool
     */
    public T useFirst() {
        if (hasAvailable()) {
            T temp = available.get(0);
            available.remove(temp);
            inUse.add(temp);
            return temp;
        }
        return null;
    }

    /**
     * Uses the given resource
     * @param resource to be used
     */
    public void useResource(T resource) {
        if (available.contains(resource)) {
            available.remove(resource);
            inUse.add(resource);
        }
    }

    /**
     * @return random available resource from resource pool
     */
    public T useRandom() {
        if (hasAvailable()) {
            Random random = new Random();
            T temp = available.get(random.nextInt(available.size()));
            available.remove(temp);
            inUse.add(temp);
            return temp;
        }
        return null;
    }

    /**
     * Return resource to resource pool after use
     * @param resource resource to be made available
     */
    public void makeAvailable(T resource) {
        inUse.remove(resource);
        available.add(resource);
    }

    /**
     * @return all resources (in use or available from resource pool)
     */
    public List<T> getAllResources() {
        List<T> temp = new ArrayList<>(inUse);
        temp.addAll(available);
        return temp;
    }
}
