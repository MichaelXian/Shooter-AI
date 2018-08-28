package Utility;

import Game.Entity;

import java.util.List;

public class Geometry {

    /**
     * Returns the entity closest to a given location. If tied, returns the first entity
     * in the arrayList to tie
     * @param entities entities to check
     * @param location
     * @return the closest entity to the given location
     */
    public static Entity closestTo(List<Entity> entities, Vector location) {
        Entity closest = entities.get(0);
        Double distance = closest.getPosition().distance(location);
        for (Entity e: entities) {
            if (e.getPosition().distance(location) < distance) {
                closest = e;
                distance = e.getPosition().distance(location);
            }
        }
        return closest;
    }

}
